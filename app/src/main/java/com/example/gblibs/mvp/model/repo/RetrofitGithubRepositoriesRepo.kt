package com.example.gblibs.mvp.model.repo

import com.example.gblibs.mvp.model.api.IDataSource
import com.example.gblibs.mvp.model.cache.RoomRepositoriesCache
import com.example.gblibs.mvp.model.entity.GithubRepository
import com.example.gblibs.mvp.model.entity.GithubUser
import com.example.gblibs.mvp.model.network.INetworkStatus
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RetrofitGithubRepositoriesRepo(val api: IDataSource, val networkStatus: INetworkStatus, val repositoriesCache: RoomRepositoriesCache) : IGithubRepositoriesRepo {
    override fun getUserRepos(user: GithubUser) = networkStatus.isOnlineSingle().flatMap { isOnline ->
        if (isOnline) {
            user.reposUrl?.let { url ->
                api.getUserRepos(url)
                    .flatMap { repositories ->
                        repositoriesCache.putRepositories(user, repositories).andThen(Single.just(repositories))
                    }
            } ?: Single.error<List<GithubRepository>>(RuntimeException("User has no repos url")).subscribeOn(
                Schedulers.io())
        } else {
            repositoriesCache.getRepositories(user)
        }
    }.subscribeOn(Schedulers.io())
}