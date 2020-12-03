package com.example.gblibs.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.gblibs.R
import com.example.gblibs.mvp.model.image.IImageLoader
import com.example.gblibs.mvp.presenter.list.IUsersListPresenter
import com.example.gblibs.mvp.view.list.UserItemView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_user.*
import kotlinx.android.synthetic.main.item_user.view.*

class UsersRvAdapter(val presenter: IUsersListPresenter, val imageLoader: IImageLoader<ImageView>) : RecyclerView.Adapter<UsersRvAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.pos = position
        holder.containerView.setOnClickListener { presenter.itemClickListener?.invoke(holder) }
        presenter.bindView(holder)
    }

    override fun getItemCount() = presenter.getCount()

    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        UserItemView, LayoutContainer {
        override var pos = -1

        override fun setLogin(text: String) {
            tv_login.text = text
        }

        override fun loadImage(url: String) {
            imageLoader.loadInto(url, iv_image)
        }
    }

}