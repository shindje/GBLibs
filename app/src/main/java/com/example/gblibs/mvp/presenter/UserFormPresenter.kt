package com.example.gblibs.mvp.presenter

import com.example.gblibs.mvp.model.entity.GithubUser
import com.example.gblibs.mvp.view.UserFormView
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router

class UserFormPresenter(val router: Router, val user: GithubUser?) : MvpPresenter<UserFormView>() {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()
    }

    fun loadData() {
        viewState.updateUserLogin(user?.login)
    }

    fun backClick(): Boolean {
        router.exit()
        return true
    }
}