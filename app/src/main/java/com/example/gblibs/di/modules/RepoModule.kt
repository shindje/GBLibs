package com.example.gblibs.di.modules

import com.example.gblibs.mvp.model.api.IDataSource
import com.example.gblibs.mvp.model.cache.IUsersCache
import com.example.gblibs.mvp.model.network.INetworkStatus
import com.example.gblibs.mvp.model.repo.IGithubUsersRepo
import com.example.gblibs.mvp.model.repo.RetrofitGithubUsersRepo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepoModule {
    @Singleton
    @Provides
    fun usersRepo(api: IDataSource, networkStatus: INetworkStatus, cache: IUsersCache): IGithubUsersRepo = RetrofitGithubUsersRepo(api, networkStatus, cache)
}