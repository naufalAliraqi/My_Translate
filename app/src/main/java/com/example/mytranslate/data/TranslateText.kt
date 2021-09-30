package com.example.mytranslate.data


import com.google.gson.annotations.SerializedName

data class TranslateText(
    @SerializedName("translatedText")
    val translatedText: String?
)