package com.example.androidtest

import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.androidtest.databinding.ActivityEditTextTestBinding

class EditTextTestActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditTextTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditTextTestBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.editText2EditTextView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().length > 8) {
                    Log.d("EditTextTestActivity", "文字制限をオーバーしました")
                } else {
                    Log.d("EditTextTestActivity", "文字制限ないです")
                }
            }
        })

        // 4. EditTextのキー編集前のイベントを登録できるか。また、編集キャンセルできるか。
        // ・可能でした。
        val regexChars = "0-9abc"
        binding.editText2EditTextView.filters = arrayOf(
            InputFilter { source, _, _, _, _, _ ->
                if (source.matches(Regex("[$regexChars]+"))) {
                    source
                } else {
                    source.replace(Regex("[^$regexChars]+"), "")
                }
            })
    }
}