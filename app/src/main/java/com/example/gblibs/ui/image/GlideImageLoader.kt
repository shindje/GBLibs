package com.example.gblibs.ui.image

import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.gblibs.mvp.model.cache.IImageCache
import com.example.gblibs.mvp.model.image.IImageLoader
import com.example.gblibs.mvp.model.network.INetworkStatus
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import java.io.ByteArrayOutputStream

class GlideImageLoader(
    val networkStatus: INetworkStatus,
    val imageCache: IImageCache
): IImageLoader<ImageView> {

    override fun loadInto(url: String, container: ImageView) {
        networkStatus.isOnlineSingle().subscribe({ isOnline ->
            if (isOnline) {
                Glide.with(container.context)
                    .asBitmap()
                    .load(url)
                    .listener(object : RequestListener<Bitmap> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Bitmap>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            //Do stuff with error
                            return false
                        }

                        override fun onResourceReady(
                            resource: Bitmap?,
                            model: Any?,
                            target: Target<Bitmap>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            //Do stuff with result
                            resource?.apply {
                                val out = ByteArrayOutputStream();
                                this.compress(Bitmap.CompressFormat.PNG, 100, out)
                                imageCache.put(url, out.toByteArray()).observeOn(AndroidSchedulers.mainThread()).subscribe()
                                out.close()
                            }

                            return false
                        }
                    })
                    .into(container)
            } else {
                imageCache.get(url).observeOn(AndroidSchedulers.mainThread()).subscribe({ byteArray ->
                    byteArray?.apply {
                        Glide.with(container.context)
                            .asBitmap()
                            .load(this)
                            .listener(object : RequestListener<Bitmap> {
                                override fun onLoadFailed(
                                    e: GlideException?,
                                    model: Any?,
                                    target: Target<Bitmap>?,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    //Do stuff with error
                                    return false
                                }

                                override fun onResourceReady(
                                    resource: Bitmap?,
                                    model: Any?,
                                    target: Target<Bitmap>?,
                                    dataSource: DataSource?,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    //Do stuff with result
                                    return false
                                }
                            })
                            .into(container)
                    }
                }, {})
            }
        }, {})
    }
}