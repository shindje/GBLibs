package com.example.gblibs.mvp.presenter

import com.example.gblibs.mvp.view.MainView
import com.example.gblibs.navigation.Screens
import com.example.gblibs.rxlearn.Operators
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class MainPresenter(): MvpPresenter<MainView>() {

    @Inject
    lateinit var router: Router

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(Screens.UsersScreen())
    }

    fun backClick() {
        router.exit()
    }

}