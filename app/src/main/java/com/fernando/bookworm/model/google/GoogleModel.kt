package com.fernando.bookworm.model.google

import com.google.gson.annotations.SerializedName


data class GoogleModel(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("etag")
    val etag: String = "",
    @SerializedName("volumeInfo")
    val volumeInfo: VolumeInfo? = null)


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

data class ISBN(
    @SerializedName("type")
    val type: String = "",
    @SerializedName("identifier")
    val identifier: String = "")

data class Image(
    @SerializedName("thumbnail")
    val thumbnail: String = "",
    @SerializedName("medium")
    val medium: String = "")