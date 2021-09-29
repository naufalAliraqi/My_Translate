package com.example.mytranslate.data


import com.google.gson.annotations.SerializedName

data class LanguageItem(
    @SerializedName("code")
    val code: String?,
    @SerializedName("name")
    val name: String?
)