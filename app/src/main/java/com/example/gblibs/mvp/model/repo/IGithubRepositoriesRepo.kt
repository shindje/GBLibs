package com.example.gblibs.mvp.model.repo

import com.example.gblibs.mvp.model.entity.GithubRepository
import com.example.gblibs.mvp.model.entity.GithubUser
import io.reactivex.rxjava3.core.Single

interface IGithubRepositoriesRepo {
    fun getUserRepos(user: GithubUser): Single<List<GithubRepository>>
}