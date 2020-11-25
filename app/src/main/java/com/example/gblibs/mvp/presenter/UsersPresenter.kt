package com.example.gblibs.mvp.presenter

import com.example.gblibs.mvp.model.entity.GithubUser
import com.example.gblibs.mvp.model.repo.GithubUsersRepo
import com.example.gblibs.mvp.presenter.list.IUsersListPresenter
import com.example.gblibs.mvp.view.UsersView
import com.example.gblibs.mvp.view.list.UserItemView
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router

class UsersPresenter(val router: Router, val usersRepo: GithubUsersRepo) : MvpPresenter<UsersView>() {

    class UsersListPresenter : IUsersListPresenter {
        override var itemClickListener: ((UserItemView) -> Unit)? = null

        val users = mutableListOf<GithubUser>()

        override fun bindView(view: UserItemView) {
            val user = users[view.pos]
            view.setLogin(user.login)
        }

        override fun getCount() = users.size
    }

    val usersListPresenter = UsersListPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()

        usersListPresenter.itemClickListener = { view ->
            //router.navigateTo(<экран пользователя>)
        }
    }

    fun loadData() {
        val users = usersRepo.getUsers()
        usersListPresenter.users.clear()
        usersListPresenter.users.addAll(users)
        viewState.updateUsersList()
    }

    fun backClick(): Boolean {
        router.exit()
        return true
    }
}