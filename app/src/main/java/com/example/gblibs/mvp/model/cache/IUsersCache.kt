package com.example.gblibs.mvp.model.cache
import com.example.gblibs.mvp.model.entity.GithubUser
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface IUsersCache {
    fun putUsers(users: List<GithubUser>): Completable
    fun getUsers(): Single<List<GithubUser>>
}