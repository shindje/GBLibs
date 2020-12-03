package com.example.gblibs.mvp.presenter

import com.example.gblibs.mvp.model.entity.GithubRepository
import com.example.gblibs.mvp.model.entity.GithubUser
import com.example.gblibs.mvp.model.repo.RetrofitGithubUsersRepo
import com.example.gblibs.mvp.presenter.list.IUserReposListPresenter
import com.example.gblibs.mvp.view.UserFormView
import com.example.gblibs.mvp.view.list.UserRepoItemView
import com.example.gblibs.navigation.Screens
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router

class UserFormPresenter(val router: Router, val usersRepoRetrofit: RetrofitGithubUsersRepo, val mainThread: Scheduler, val user: GithubUser?) : MvpPresenter<UserFormView>() {
    class UserReposListPresenter : IUserReposListPresenter {
        override var itemClickListener: ((UserRepoItemView) -> Unit)? = null

        val userRepos = mutableListOf<GithubRepository>()

        override fun bindView(view: UserRepoItemView) {
            val repo = userRepos[view.pos]
            view.setName(repo.name)
            view.setDescription(repo.description)
        }

        override fun getCount() = userRepos.size
    }

    val userReposListPresenter = UserReposListPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()

        userReposListPresenter.itemClickListener = { view ->
            router.navigateTo(Screens.UserRepoScreen(userReposListPresenter.userRepos[view.pos]))
        }
    }

    fun loadData() {
        usersRepoRetrofit.getUserRepos(user?.reposUrl)
            .observeOn(mainThread)
            .subscribe({ repos ->
                userReposListPresenter.userRepos.clear()
                userReposListPresenter.userRepos.addAll(repos)
                viewState.updateUserReposList()
            }, {
                println("Error: ${it.message}")
            })

        viewState.updateUserLogin(user?.login)
    }

    fun backClick(): Boolean {
        router.exit()
        return true
    }
}