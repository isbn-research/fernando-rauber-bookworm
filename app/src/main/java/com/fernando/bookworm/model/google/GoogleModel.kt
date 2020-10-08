package com.fernando.bookworm.model.google

import com.google.gson.annotations.SerializedName


data class GoogleModel(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("etag")
    val etag: String = "",
    @SerializedName("volumeInfo")
    val volumeInfo: VolumeInfo? = null)