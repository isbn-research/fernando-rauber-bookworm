package com.fernando.bookworm.model.google

import com.google.gson.annotations.SerializedName

data class VolumeInfo(
    @SerializedName("title")
    val title: String = "",
    @SerializedName("authors")
    val authors: List<String>?,
    @SerializedName("publisher")
    val publisher: String? = "",
    @SerializedName("publishedDate")
    val publishedDate: String? = "",
    @SerializedName("description")
    val description: String? = "",
    @SerializedName("previewLink")
    val bookLink: String? = "",
    @SerializedName("pageCount")
    val pageCount: Int = 0,
    @SerializedName("subtitle")
    val subtitle: String = "",
    @SerializedName("averageRating")
    val averageRating: Double = 0.0,
    @SerializedName("ratingsCount")
    val ratingsCount: Int = 0,
    @SerializedName("industryIdentifiers")
    val isbn: List<ISBN>?,
    @SerializedName("imageLinks")
    val imageLinks: Image?,
)