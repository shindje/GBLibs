package com.example.gblibs.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gblibs.R
import com.example.gblibs.mvp.presenter.list.IUsersListPresenter
import com.example.gblibs.mvp.view.list.UserItemView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_user.view.*

class UsersRvAdapter(val presenter: IUsersListPresenter) : RecyclerView.Adapter<UsersRvAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)).apply {
            containerView.setOnClickListener {
                presenter.itemClickListener?.invoke(this)
            }
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.pos = position
        presenter.bindView(holder)
    }

    override fun getItemCount() = presenter.getCount()

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        UserItemView, LayoutContainer {
        override var pos = -1

        override fun setLogin(text: String) = with(containerView){
            tv_login.text = text
        }
    }

}