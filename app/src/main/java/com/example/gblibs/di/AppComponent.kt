package com.example.gblibs.di

import com.example.gblibs.di.modules.*
import com.example.gblibs.mvp.presenter.MainPresenter
import com.example.gblibs.mvp.presenter.UsersPresenter
import com.example.gblibs.ui.activity.MainActivity
import com.example.gblibs.ui.fragment.UserFormFragment
import com.example.gblibs.ui.fragment.UserRepoFragment
import com.example.gblibs.ui.fragment.UsersFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        CiceroneModule::class,
        CacheModule::class,
        ApiModule::class,
        RepoModule::class
    ]
)

interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(mainPresenter: MainPresenter)

    fun inject(usersPresenter: UsersPresenter)

    //При выполнении практического задания это должно отсюда уйти
    fun inject(userFormFragment: UserFormFragment)
    fun inject(repositoryFragment: UserRepoFragment)
    fun inject(usersFragment: UsersFragment)
}