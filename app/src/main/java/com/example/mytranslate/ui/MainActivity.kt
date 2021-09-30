package com.example.mytranslate.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.example.mytranslate.R
import com.example.mytranslate.data.Language
import com.example.mytranslate.data.TranslateText
import com.example.mytranslate.databinding.ActivityMainBinding
import com.example.mytranslate.repository.TranslateRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

//    val spinnerItem = mutableListOf<String>()
    var languageItem = mutableMapOf<String,String>()
    lateinit var sourceLanguage : String
    lateinit var targetLanguage : String

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        getPrayRequest()
        initEnteredText()
    }

    private fun initEnteredText() {
        binding.enteredText.doOnTextChanged { text, start, before, count ->
            if (text.toString() != "" && sourceLanguage.isNotEmpty() && targetLanguage.isNotEmpty())
                makeTranslateRequest(text.toString())
        }
    }

    private fun makeTranslateRequest(text: String) {
        lifecycleScope.launch {
            TranslateRepository.getInfoTans(text, sourceLanguage, targetLanguage).onCompletion {  }.catch {
                Toast.makeText(
                    this@MainActivity,
                    "error can't access sorry ",
                    Toast.LENGTH_SHORT
                ).show()
            }.collect { getResultTrans(it) }
        }
    }

    private fun getResultTrans(response: Status<TranslateText>) {
        return when (response) {
            is Status.Error -> {
                Toast.makeText(
                    this@MainActivity,
                    "error can't access sorry ",
                    Toast.LENGTH_SHORT
                ).show()
            }
            is Status.Loading -> {
                Toast.makeText(
                    this@MainActivity,
                    "Loading access  ",
                    Toast.LENGTH_SHORT
                ).show()
            }
            is Status.Success -> {
                viewTranslatedText(response.responseData)
            }
        }
    }

    private fun viewTranslatedText(responseData: TranslateText) {
        binding.translatedText.text = responseData.translatedText
    }

    private fun getPrayRequest() {

        lifecycleScope.launch {
            TranslateRepository.getInfoPray().onCompletion {  }.catch {
                Toast.makeText(
                    this@MainActivity,
                    "error can't access sorry ",
                    Toast.LENGTH_SHORT
                ).show()
            }.collect { getResultPray(it) }
        }
    }

    fun getResultPray(response: Status<Language>) {
        return when (response) {
            is Status.Error -> {
                Toast.makeText(
                    this@MainActivity,
                    "error can't access sorry ",
                    Toast.LENGTH_SHORT
                ).show()
            }
            is Status.Loading -> {
                Toast.makeText(
                    this@MainActivity,
                    "Loading access  ",
                    Toast.LENGTH_SHORT
                ).show()
            }
            is Status.Success -> {
                bindData(response.responseData)
            }
        }
    }

    private fun bindData(responseData: Language) {
        Log.i("Main", responseData[0].code.toString())
        responseData.forEach { languageItem.put(it.code.toString(), it.name.toString()) }
        val spinner_Adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languageItem.values.toTypedArray())
        initSpinner(binding.sourceLanguageSpinner, spinner_Adapter)
        initSpinner(binding.targetLanguageSpinner, spinner_Adapter)
    }

    private fun initSpinner(spinner: Spinner, spinner_Adapter: Adapter?)
    {
        spinner.apply {
            adapter = spinner_Adapter as SpinnerAdapter?
            onItemSelectedListener = object  : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    if (spinner == binding.sourceLanguageSpinner) {
                        Toast.makeText(
                            this@MainActivity,
                            "Source ${languageItem.values.toTypedArray()[p2]}",
                            Toast.LENGTH_SHORT
                        ).show()
                        sourceLanguage = languageItem.keys.toTypedArray()[p2]
                    }
                    else {
                        Toast.makeText(
                            this@MainActivity,
                            "Target ${languageItem.values.toTypedArray()[p2]}",
                            Toast.LENGTH_SHORT
                        ).show()
                        targetLanguage = languageItem.keys.toTypedArray()[p2]
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

            }
        }
    }
}