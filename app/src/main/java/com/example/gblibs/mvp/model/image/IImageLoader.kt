package com.example.gblibs.mvp.model.image

interface IImageLoader<T> {
    fun loadInto(url: String, container: T)
}