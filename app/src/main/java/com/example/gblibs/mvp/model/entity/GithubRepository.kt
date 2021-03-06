package com.example.gblibs.mvp.model.entity

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GithubRepository (
    @Expose val id: String? = null,
    @Expose val name: String,
    @Expose val description: String?,
    @Expose val forksCount: String?
) : Parcelable