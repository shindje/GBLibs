package com.example.gblibs.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gblibs.R
import com.example.gblibs.mvp.model.entity.GithubRepository
import com.example.gblibs.mvp.presenter.UserRepoPresenter
import com.example.gblibs.mvp.view.UserRepoView
import com.example.gblibs.ui.App
import com.example.gblibs.ui.BackButtonListener
import kotlinx.android.synthetic.main.fragment_repo.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class UserRepoFragment: MvpAppCompatFragment(), UserRepoView, BackButtonListener {
    companion object {
        fun newInstance(repo: GithubRepository) = UserRepoFragment().apply {
            arguments = Bundle().apply {
                putParcelable("repo", repo)
            }
        }
    }

    val presenter by moxyPresenter {
        val repo: GithubRepository? = arguments?.getParcelable("repo")
        UserRepoPresenter(repo).apply {
            App.instance.appComponent.inject(this)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        View.inflate(context, R.layout.fragment_repo, null)

    override fun init() {
        tv_repo_name.setText(presenter.repo?.name)
        tv_forks.setText(presenter.repo?.forksCount)
    }

    override fun backPressed() = presenter.backClick()

}