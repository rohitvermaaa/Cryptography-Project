package com.example.myapplication

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding!!.encryptBtn.setOnClickListener {
            val plainText = binding!!.encryptTextEt.text.toString()
            val key = binding!!.keyEnEt.text.toString()
            val encryptedText = vigenereEncrypt(plainText, key)
            binding!!.encryptTv.text = encryptedText
            hideKeyboard()
        }

        binding!!.decryptBtn.setOnClickListener {
            val cipherText = binding!!.decryptTextEt.text.toString()
            val key = binding!!.keyDeEt.text.toString()
            val decryptedText = vigenereDecrypt(cipherText, key)
            binding!!.decryptTv.text = decryptedText
            hideKeyboard()
        }

        binding!!.copyToClipboardEn.setOnClickListener {
            val textToCopy = binding!!.encryptTv.text.toString()
            copyToClipboard(textToCopy)
        }
        binding!!.copyToClipboardDe.setOnClickListener {
            val textToCopy = binding!!.decryptTv.text.toString()
            copyToClipboard(textToCopy)
        }
    }

    private fun vigenereEncrypt(plainText: String, key: String): String {
        val cleanText = plainText.filter { it.isLetter() }
        val cleanKey = key.filter { it.isLetter() }
        return cleanText.mapIndexed { index, c ->
            val offset = cleanKey[index % cleanKey.length].toUpperCase() - 'A'
            if (c.isUpperCase()) {
                'A' + (c - 'A' + offset) % 26
            } else {
                'a' + (c - 'a' + offset) % 26
            }
        }.joinToString("")
    }

    private fun vigenereDecrypt(cipherText: String, key: String): String {
        val cleanText = cipherText.filter { it.isLetter() }
        val cleanKey = key.filter { it.isLetter() }
        return cleanText.mapIndexed { index, c ->
            val offset = cleanKey[index % cleanKey.length].toUpperCase() - 'A'
            if (c.isUpperCase()) {
                'A' + (c - 'A' - offset + 26) % 26
            } else {
                'a' + (c - 'a' - offset + 26) % 26
            }
        }.joinToString("")
    }

    private fun hideKeyboard() {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        currentFocus?.let {
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    private fun copyToClipboard(text: String) {
        val clipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("text", text)
        clipboardManager.setPrimaryClip(clipData)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
