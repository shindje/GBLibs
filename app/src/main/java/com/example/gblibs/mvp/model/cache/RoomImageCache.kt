package com.example.gblibs.mvp.model.cache

import android.os.Environment
import com.example.gblibs.mvp.model.entity.room.RoomImageCached
import com.example.gblibs.mvp.model.entity.room.db.Database
import com.example.gblibs.ui.App
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.File
import java.net.URLEncoder

class RoomImageCache( val app: App, val db: Database): IImageCache {

    override fun put(url: String, byteArray: ByteArray) =
        Completable.fromCallable {
            val fileName = URLEncoder.encode(url, "UTF-8")
            val file = File(
                app.applicationContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
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