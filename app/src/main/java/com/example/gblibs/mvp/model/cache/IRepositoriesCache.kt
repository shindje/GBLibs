package com.example.gblibs.mvp.model.cache

import com.example.gblibs.mvp.model.entity.GithubRepository
import com.example.gblibs.mvp.model.entity.GithubUser
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface IRepositoriesCache {
    fun putRepositories(user: GithubUser, repositories: List<GithubRepository>): Completable
    fun getRepositories(user: GithubUser): Single<List<GithubRepository>>
}