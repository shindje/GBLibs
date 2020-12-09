package com.example.gblibs.mvp.model.repo

import com.example.gblibs.mvp.model.api.IDataSource
import com.example.gblibs.mvp.model.entity.GithubRepository
import com.example.gblibs.mvp.model.entity.GithubUser
import com.example.gblibs.mvp.model.entity.room.RoomGithubRepository
import com.example.gblibs.mvp.model.entity.room.RoomGithubUser
import com.example.gblibs.mvp.model.entity.room.db.Database
import com.example.gblibs.mvp.model.network.INetworkStatus
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RetrofitGithubUsersRepo(val api: IDataSource, val networkStatus: INetworkStatus, val db: Database) : IGithubUsersRepo {
    override fun getUsers() = networkStatus.isOnlineSingle().flatMap { isOnline ->
        if (isOnline) {
            api.getUsers()
                .flatMap { users ->
                    Single.fromCallable {
                        val roomUsers = users.map { user -> RoomGithubUser(user.id ?: "", user.login ?: "", user.avatarUrl ?: "", user.reposUrl ?: "") }
                        db.userDao.insert(roomUsers)
                        users
                    }
                }
        } else {
            Single.fromCallable {
                db.userDao.getAll().map { roomUser ->
                    GithubUser(roomUser.id, roomUser.login, roomUser.avatarUrl, roomUser.reposUrl)
                }
            }
        }
    }.subscribeOn(Schedulers.io())

    override fun getUserRepos(user: GithubUser) = networkStatus.isOnlineSingle().flatMap { isOnline ->
        if (isOnline) {
            user.reposUrl?.let { url ->
                api.getUserRepos(url)
                    .flatMap { repositories ->
                        Single.fromCallable {
                            val roomUser = user.login?.let { db.userDao.findByLogin(it) } ?: throw RuntimeException("No such user in cache")
                            val roomRepos = repositories.map { RoomGithubRepository(it.id ?: "", it.name ?: "", it.description ?: "", it.forksCount ?: "", roomUser.id) }
                            db.repositoryDao.insert(roomRepos)
                            repositories
                        }
                    }
            } ?: Single.error<List<GithubRepository>>(RuntimeException("User has no repos url")).subscribeOn(Schedulers.io())
        } else {
            Single.fromCallable {
                val roomUser = user.login?.let { db.userDao.findByLogin(it) } ?: throw RuntimeException("No such user in cache")
                db.repositoryDao.findForUser(roomUser.id).map { GithubRepository(it.id, it.name, it.description, it.forksCount) }
            }

        }
    }.subscribeOn(Schedulers.io())
}