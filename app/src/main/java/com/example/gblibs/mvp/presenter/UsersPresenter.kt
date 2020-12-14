package com.example.gblibs.mvp.presenter

import com.example.gblibs.mvp.model.entity.GithubUser
import com.example.gblibs.mvp.model.repo.IGithubUsersRepo
import com.example.gblibs.mvp.model.repo.RetrofitGithubUsersRepo
import com.example.gblibs.mvp.presenter.list.IUsersListPresenter
import com.example.gblibs.mvp.view.UsersView
import com.example.gblibs.mvp.view.list.UserItemView
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import com.example.gblibs.navigation.Screens
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class UsersPresenter(val mainThread: Scheduler) : MvpPresenter<UsersView>() {
    @Inject
    lateinit var usersRepo: IGithubUsersRepo
    @Inject
    lateinit var router: Router

    class UsersListPresenter : IUsersListPresenter {
        override var itemClickListener: ((UserItemView) -> Unit)? = null

        val users = mutableListOf<GithubUser>()

        override fun bindView(view: UserItemView) {
            val user = users[view.pos]
            view.setLogin(user.login)
            user.avatarUrl?.let { view.loadImage(it) }
        }

        override fun getCount() = users.size
    }

    val usersListPresenter = UsersListPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()

        usersListPresenter.itemClickListener = { view ->
            router.navigateTo(Screens.UserFormScreen(usersListPresenter.users[view.pos]))
        }
    }

    fun loadData() {
        usersRepo.getUsers()
            .observeOn(mainThread)
            .subscribe({ repos ->
                usersListPresenter.users.clear()
                usersListPresenter.users.addAll(repos)
                viewState.updateUsersList()
            }, {
                println("Error: ${it.message}")
            })
    }

    fun backClick(): Boolean {
        router.exit()
        return true
    }
}