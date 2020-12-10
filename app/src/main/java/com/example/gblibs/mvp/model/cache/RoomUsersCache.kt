package com.example.gblibs.mvp.model.cache

import com.example.gblibs.mvp.model.entity.GithubUser
import com.example.gblibs.mvp.model.entity.room.RoomGithubUser
import com.example.gblibs.mvp.model.entity.room.db.Database
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class RoomUsersCache(val db: Database): IUsersCache {

    override fun putUsers(users: List<GithubUser>) =
        Completable.fromCallable {
            val roomUsers = users.map { user -> RoomGithubUser(user.id ?: "", user.login ?: "", user.avatarUrl ?: "", user.reposUrl ?: "") }
            db.userDao.insert(roomUsers)
            users
        }

    override fun getUsers() =
        Single.fromCallable {
            db.userDao.getAll().map { roomUser ->
                GithubUser(roomUser.id, roomUser.login, roomUser.avatarUrl, roomUser.reposUrl)
            }
        }
}