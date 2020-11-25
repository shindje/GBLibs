package com.example.gblibs.mvp.view

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface UserFormView: MvpView {
    fun init()
    fun updateUserLogin(login: String?)
}