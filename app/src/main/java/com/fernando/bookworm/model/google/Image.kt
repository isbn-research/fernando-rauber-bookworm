package com.fernando.bookworm.model.google

import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("thumbnail")
    val thumbnail: String = "",
    @SerializedName("medium")
    val medium: String = "")