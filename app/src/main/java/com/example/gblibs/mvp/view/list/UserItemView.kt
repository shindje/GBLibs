package com.example.gblibs.mvp.view.list

interface UserItemView : IItemView {
    fun setLogin(text: String)
    fun loadImage(url: String)
}