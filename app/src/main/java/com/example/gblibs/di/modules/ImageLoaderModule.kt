package com.example.gblibs.di.modules

import android.widget.ImageView
import com.example.gblibs.mvp.model.cache.IImageCache
import com.example.gblibs.mvp.model.image.IImageLoader
import com.example.gblibs.mvp.model.network.INetworkStatus
import com.example.gblibs.ui.image.GlideImageLoader
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ImageLoaderModule {
    @Singleton
    @Provides
    fun imageLoader(networkStatus: INetworkStatus, cache: IImageCache): IImageLoader<ImageView> = GlideImageLoader(networkStatus, cache)
}