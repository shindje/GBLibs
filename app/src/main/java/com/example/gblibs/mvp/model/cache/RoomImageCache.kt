package com.example.gblibs.mvp.model.cache

import android.content.Context
import android.os.Environment
import com.example.gblibs.mvp.model.entity.room.RoomImageCached
import com.example.gblibs.mvp.model.entity.room.db.Database
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.File
import java.net.URLEncoder

class RoomImageCache(val db: Database, val context: Context): IImageCache {

    override fun put(url: String, byteArray: ByteArray) =
        Completable.fromCallable {
            val fileName = URLEncoder.encode(url, "UTF-8")
            val file = File(
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                fileName
            )
            file.createNewFile()
            file.writeBytes(byteArray)

            val roomImageCached = RoomImageCached(url, file.path)
            db.imageCachedDao.insert(roomImageCached)
        }.subscribeOn(Schedulers.io())

    override fun get(url: String): Single<ByteArray> =
        Single.create<ByteArray> { emitter ->
            db.imageCachedDao.findByUrl(url)?.let {
                emitter.onSuccess(File(it.path).readBytes())
            }?: emitter.onError(RuntimeException("No cached image found by given url"))
        }.subscribeOn(Schedulers.io())
}