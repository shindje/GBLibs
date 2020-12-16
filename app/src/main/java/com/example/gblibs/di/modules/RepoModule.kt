package com.example.gblibs.di.modules

import com.example.gblibs.mvp.model.api.IDataSource
import com.example.gblibs.mvp.model.cache.IRepositoriesCache
import com.example.gblibs.mvp.model.cache.IUsersCache
import com.example.gblibs.mvp.model.network.INetworkStatus
import com.example.gblibs.mvp.model.repo.IGithubRepositoriesRepo
import com.example.gblibs.mvp.model.repo.IGithubUsersRepo
import com.example.gblibs.mvp.model.repo.RetrofitGithubRepositoriesRepo
import com.example.gblibs.mvp.model.repo.RetrofitGithubUsersRepo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepoModule {
    @Singleton
    @Provides
    fun usersRepo(api: IDataSource, networkStatus: INetworkStatus, cache: IUsersCache): IGithubUsersRepo = RetrofitGithubUsersRepo(api, networkStatus, cache)

    @Singleton
    @Provides
    fun repositoriesRepo(api: IDataSource, networkStatus: INetworkStatus, cache: IRepositoriesCache): IGithubRepositoriesRepo = RetrofitGithubRepositoriesRepo(api, networkStatus, cache)
}