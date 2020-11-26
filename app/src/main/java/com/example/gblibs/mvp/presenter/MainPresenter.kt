package com.example.gblibs.mvp.presenter

import com.example.gblibs.mvp.view.MainView
import com.example.gblibs.navigation.Screens
import com.example.gblibs.rxlearn.Operators
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router

class MainPresenter(val router: Router): MvpPresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(Screens.UsersScreen())

        Operators().exec()
    }

    fun backClick() {
        router.exit()
    }

}