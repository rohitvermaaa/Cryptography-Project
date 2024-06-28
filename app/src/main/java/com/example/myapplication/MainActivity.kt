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

//        binding!!.encryptTv.setTextColor(resources.getColor(android.R.color.black))
//        binding!!.encryptTv.setHintTextColor(resources.getColor(android.R.color.black))
//        binding!!.decryptTv.setTextColor(resources.getColor(android.R.color.black))
//        binding!!.decryptTv.setHintTextColor(resources.getColor(android.R.color.black))

        binding!!.encryptBtn.setOnClickListener {
            Toast.makeText(this, "Encrypted", Toast.LENGTH_SHORT).show()
            binding!!.encryptTextEt.text.clear()
            hideKeyboard()
        }

        binding!!.decryptBtn.setOnClickListener {
            Toast.makeText(this, "Decrypted", Toast.LENGTH_SHORT).show()
            binding!!.decryptTextEt.text.clear()
            hideKeyboard()
        }

        binding!!.copyToClipboardEn.setOnClickListener {
            val textToCopy = binding!!.encryptTv.text.toString()
            copyToClipboard(textToCopy)
        }
        binding!!.copyToClipboardDe.setOnClickListener {
            val textToCopy = binding!!.encryptTv.text.toString()
            copyToClipboard(textToCopy)
        }
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
        Toast.makeText(this, "Copied", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
