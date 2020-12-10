package com.example.gblibs.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gblibs.R
import com.example.gblibs.mvp.model.api.ApiHolder
import com.example.gblibs.mvp.model.cache.RoomImageCache
import com.example.gblibs.mvp.model.cache.RoomUsersCache
import com.example.gblibs.mvp.model.entity.room.db.Database
import com.example.gblibs.mvp.model.repo.RetrofitGithubUsersRepo
import com.example.gblibs.mvp.presenter.UsersPresenter
import com.example.gblibs.mvp.view.UsersView
import com.example.gblibs.ui.App
import com.example.gblibs.ui.BackButtonListener
import com.example.gblibs.ui.adapter.UsersRvAdapter
import com.example.gblibs.ui.image.GlideImageLoader
import com.example.gblibs.ui.network.AndroidNetworkStatus
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_users.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class UsersFragment : MvpAppCompatFragment(), UsersView, BackButtonListener {

    companion object {
        fun newInstance() = UsersFragment()
    }

    val presenter by moxyPresenter {
        UsersPresenter(App.instance.router, RetrofitGithubUsersRepo(ApiHolder.api, AndroidNetworkStatus(requireContext()), RoomUsersCache(Database.getInstance())), AndroidSchedulers.mainThread())
    }

    val adapter by lazy {
        UsersRvAdapter(presenter.usersListPresenter, GlideImageLoader(AndroidNetworkStatus(requireContext()), RoomImageCache(Database.getInstance(), requireContext()), AndroidSchedulers.mainThread() ))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        View.inflate(context, R.layout.fragment_users, null)

    override fun init() {
        rv_users.layoutManager = LinearLayoutManager(requireContext())
        rv_users.adapter = adapter
    }

    override fun updateUsersList() {
        adapter.notifyDataSetChanged()
    }

    override fun backPressed() = presenter.backClick()
}