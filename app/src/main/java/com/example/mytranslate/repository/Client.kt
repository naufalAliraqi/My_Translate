package com.example.mytranslate.repository

import android.util.Log
import com.example.mytranslate.data.Language
import com.example.mytranslate.ui.Status
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request

object Client {

    //make first request

    private val okHttpClient = OkHttpClient()
    private val urlokhttp = "https://translate.argosopentech.com/languages"
    private val gson = Gson()

    fun initRequest(): Status<Language> {

        Log.i("Trans Clint", "make Request")

        val request = Request.Builder().url(urlokhttp).build()
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



}