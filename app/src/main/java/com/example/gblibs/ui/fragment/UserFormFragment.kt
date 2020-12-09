package com.example.gblibs.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gblibs.R
import com.example.gblibs.mvp.model.api.ApiHolder
import com.example.gblibs.mvp.model.cache.RoomRepositoriesCache
import com.example.gblibs.mvp.model.entity.GithubUser
import com.example.gblibs.mvp.model.entity.room.db.Database
import com.example.gblibs.mvp.model.repo.RetrofitGithubRepositoriesRepo
import com.example.gblibs.mvp.presenter.UserFormPresenter
import com.example.gblibs.mvp.view.UserFormView
import com.example.gblibs.ui.App
import com.example.gblibs.ui.BackButtonListener
import com.example.gblibs.ui.adapter.UserReposRvAdapter
import com.example.gblibs.ui.network.AndroidNetworkStatus
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_user_form.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class UserFormFragment: MvpAppCompatFragment(), UserFormView, BackButtonListener {
    companion object {
        fun newInstance(user: GithubUser) = UserFormFragment().apply {
            arguments = Bundle().apply {
                putParcelable("user", user)
            }
        }
    }

    val presenter by moxyPresenter {
        val user: GithubUser = arguments?.getParcelable<GithubUser>("user") as GithubUser
        UserFormPresenter(App.instance.router,
            RetrofitGithubRepositoriesRepo(ApiHolder.api, AndroidNetworkStatus(requireContext()), RoomRepositoriesCache(Database.getInstance())),
            AndroidSchedulers.mainThread(),
            user)
    }

    val adapter by lazy {
        UserReposRvAdapter(presenter.userReposListPresenter)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        View.inflate(context, R.layout.fragment_user_form, null)

    override fun init() {
        rv_user_repos.layoutManager = LinearLayoutManager(requireContext())
        rv_user_repos.adapter = adapter
    }

    override fun updateUserLogin(login: String?) {
        loginTextView.setText(login)
    }

    override fun updateUserReposList() {
        adapter.notifyDataSetChanged()
    }

    override fun backPressed() = presenter.backClick()

}