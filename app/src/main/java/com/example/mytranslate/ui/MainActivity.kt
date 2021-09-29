package com.example.mytranslate.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.mytranslate.data.Language
import com.example.mytranslate.databinding.ActivityMainBinding
import com.example.mytranslate.repository.TranslateRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        makeTranslateRequest()
    }

    private fun makeTranslateRequest() {
        lifecycleScope.launch {
            TranslateRepository.getInfoPray().onCompletion {  }.catch {
                Toast.makeText(
                    this@MainActivity,
                    "error can't access sorry ",
                    Toast.LENGTH_SHORT
                ).show()
            }.collect { getResultTranslate(it) }

                    //collect { getResultTranslate(it) }
        }
    }

    private fun getResultTranslate(response: Status<Language>) {

    }
}