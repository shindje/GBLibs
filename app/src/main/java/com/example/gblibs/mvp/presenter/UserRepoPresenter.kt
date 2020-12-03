package com.example.gblibs.mvp.presenter

import com.example.gblibs.mvp.model.entity.GithubRepository
import com.example.gblibs.mvp.view.UserRepoView
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router

class UserRepoPresenter(val router: Router, val repo: GithubRepository?) : MvpPresenter<UserRepoView>() {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
    }

    fun backClick(): Boolean {
        router.exit()
        return true
    }
}