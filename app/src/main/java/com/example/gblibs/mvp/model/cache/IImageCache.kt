package com.example.gblibs.mvp.model.cache

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface IImageCache {
    fun put(url: String, byteArray: ByteArray): Completable
    fun get(url: String): Single<ByteArray>
}