package com.example.gblibs.mvp.view.list

interface UserRepoItemView : IItemView {
    fun setName(text: String)
    fun setDescription(text: String?)
}