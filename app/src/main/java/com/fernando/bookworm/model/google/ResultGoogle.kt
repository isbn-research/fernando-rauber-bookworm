package com.fernando.bookworm.model.google

import com.google.gson.annotations.SerializedName

data class ResultGoogle(@SerializedName("totalItems")
                        val totalItems: Int = 0,
                        @SerializedName("items")
                        val items: List<GoogleModel>? = null)


