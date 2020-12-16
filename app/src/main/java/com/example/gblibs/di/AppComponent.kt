package com.example.gblibs.di

import com.example.gblibs.di.modules.*
import com.example.gblibs.mvp.presenter.MainPresenter
import com.example.gblibs.mvp.presenter.UserFormPresenter
import com.example.gblibs.mvp.presenter.UserRepoPresenter
import com.example.gblibs.mvp.presenter.UsersPresenter
import com.example.gblibs.ui.activity.MainActivity
import com.example.gblibs.ui.adapter.UsersRvAdapter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        CiceroneModule::class,
        CacheModule::class,
        ApiModule::class,
        RepoModule::class,
        ImageLoaderModule::class
    ]
)

interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(mainPresenter: MainPresenter)

    fun inject(usersPresenter: UsersPresenter)
    fun inject(userFormPresenter: UserFormPresenter)
    fun inject(usersRepoPresenter: UserRepoPresenter)
    fun inject(usersRvAdapter: UsersRvAdapter)
}