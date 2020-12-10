package com.example.gblibs.mvp.model.cache

import com.example.gblibs.mvp.model.entity.GithubRepository
import com.example.gblibs.mvp.model.entity.GithubUser
import com.example.gblibs.mvp.model.entity.room.RoomGithubRepository
import com.example.gblibs.mvp.model.entity.room.RoomGithubUser
import com.example.gblibs.mvp.model.entity.room.db.Database
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class RoomRepositoriesCache(val db: Database) : IRepositoriesCache {

    override fun putRepositories(user: GithubUser, repositories: List<GithubRepository>) =
        Completable.fromCallable {
            val roomUser = user.login?.let { db.userDao.findByLogin(it) } ?: throw RuntimeException("No such user in cache")
            val roomRepos = repositories.map { RoomGithubRepository(it.id ?: "", it.name ?: "", it.description ?: "", it.forksCount ?: "", roomUser.id) }
            db.repositoryDao.insert(roomRepos)
            repositories
        }

    override fun getRepositories(user: GithubUser) =
        Single.fromCallable {
            val roomUser = user.login?.let { db.userDao.findByLogin(it) } ?: throw RuntimeException("No such user in cache")
            db.repositoryDao.findForUser(roomUser.id).map { GithubRepository(it.id, it.name, it.description, it.forksCount) }
        }

}