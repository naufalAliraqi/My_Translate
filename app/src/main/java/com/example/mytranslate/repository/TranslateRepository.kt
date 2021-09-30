package com.example.mytranslate.repository

import com.example.mytranslate.data.Language
import com.example.mytranslate.data.TranslateText
import com.example.mytranslate.ui.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

object TranslateRepository {

    fun getInfoPray() = flow<Status<Language>>{
        emit(Status.Loading)
        emit(Client.initRequest())
    }.flowOn(Dispatchers.IO)

    fun getInfoTans(text: String, sourceLanguage: String, targetLanguage : String) = flow<Status<TranslateText>>{
        emit(Status.Loading)
        emit(Client.translateRequest(text, sourceLanguage, targetLanguage))
    }.flowOn(Dispatchers.IO)
}