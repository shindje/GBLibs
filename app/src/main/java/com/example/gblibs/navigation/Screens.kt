package com.example.gblibs.navigation

import com.example.gblibs.mvp.model.entity.GithubRepository
import com.example.gblibs.mvp.model.entity.GithubUser
import com.example.gblibs.ui.fragment.UserFormFragment
import com.example.gblibs.ui.fragment.UserRepoFragment
import com.example.gblibs.ui.fragment.UsersFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {
    class UsersScreen() : SupportAppScreen() {
        override fun getFragment() = UsersFragment.newInstance()
    }

    class UserFormScreen(val user: GithubUser) : SupportAppScreen() {
        override fun getFragment() = UserFormFragment.newInstance(user)
    }

    class UserRepoScreen(val repo: GithubRepository) : SupportAppScreen() {
        override fun getFragment() = UserRepoFragment.newInstance(repo)
    }
}