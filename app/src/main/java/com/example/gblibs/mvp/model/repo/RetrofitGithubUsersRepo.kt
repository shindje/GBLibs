package com.example.gblibs.mvp.model.repo

import com.example.gblibs.mvp.model.api.IDataSource
import com.example.gblibs.mvp.model.cache.IUsersCache
import com.example.gblibs.mvp.model.network.INetworkStatus
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RetrofitGithubUsersRepo(val api: IDataSource, val networkStatus: INetworkStatus, val usersCache: IUsersCache) : IGithubUsersRepo {
    override fun getUsers() = networkStatus.isOnlineSingle().flatMap { isOnline ->
        if (isOnline) {
            api.getUsers()
                .flatMap { users ->
                    usersCache.putUsers(users).andThen(Single.just(users))
                }
        } else {
            usersCache.getUsers()
        }
    }.subscribeOn(Schedulers.io())
}