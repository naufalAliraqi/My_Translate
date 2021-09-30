package com.example.mytranslate.repository

import android.util.Log
import com.example.mytranslate.data.Language
import com.example.mytranslate.data.TranslateText
import com.example.mytranslate.ui.Status
import com.google.gson.Gson
import okhttp3.FormBody
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request

object Client {

    //make first request

    private val okHttpClient = OkHttpClient()
//    private val languageUrl= "https://translate.argosopentech.com/languages"
//    private val translateUrl= "https://translate.argosopentech.com/languages"
    private val gson = Gson()



    lateinit var translateUrl : HttpUrl
    val languageUrl = HttpUrl.Builder()
            .scheme("https")
            .host("translate.argosopentech.com")
            .addPathSegment("/languages")
            .build()

    private fun makeTranslateUrl(text: String, sourceLanguage: String, targetLanguage : String) {
            translateUrl = HttpUrl.Builder()
            .scheme("https")
            .addPathSegment("/translate")
            .host("translate.argosopentech.com")
            .addQueryParameter("q", text)
            .addQueryParameter("source", sourceLanguage)
            .addQueryParameter("target", targetLanguage)
            .build()
    }

    fun initRequest(): Status<Language> {

        Log.i("mega", "make Request")

        val request = Request.Builder().url(languageUrl).build()
        val response = okHttpClient.newCall(request).execute()

        return if (response.isSuccessful) {
            val parserResponse = gson.fromJson(
                response.body?.string(),
                Language::class.java
            )
            Status.Success(parserResponse)
        } else {
            Status.Error(response.message)
        }
    }

    fun translateRequest(text: String, sourceLanguage: String, targetLanguage : String): Status<TranslateText> {

        Log.i("mega", "make Request")

        makeTranslateUrl(text, sourceLanguage, targetLanguage)
        val request = Request.Builder().url(translateUrl).post(FormBody.Builder().build()).build()
        val response = okHttpClient.newCall(request).execute()

        return if (response.isSuccessful) {
            val parserResponse = gson.fromJson(
                response.body?.string(),
                TranslateText::class.java
            )
            Status.Success(parserResponse)
        } else {
            Status.Error(response.message)
        }
    }



}