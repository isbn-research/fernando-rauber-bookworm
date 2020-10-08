package com.fernando.bookworm.model.google

import com.google.gson.annotations.SerializedName

data class ISBN(
    @SerializedName("type")
    val type: String = "",
    @SerializedName("identifier")
    val identifier: String = "")
