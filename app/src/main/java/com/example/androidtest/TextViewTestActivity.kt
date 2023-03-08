package com.example.androidtest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androidtest.databinding.ActivityTextViewTestBinding

class TextViewTestActivity : AppCompatActivity() {
    lateinit var binding: ActivityTextViewTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTextViewTestBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // 5. TextViewの押下時のイベントを登録できるか。
        // ・可能でした。
        binding.text1TextViewSwitchTextView.setOnClickListener {
            binding.text1TextView.isEnabled = !binding.text1TextView.isEnabled
        }

        binding.text1TextViewEnableTextView.setOnClickListener {
            binding.text1TextView.isEnabled = true
        }

        binding.okButtonDisableButton.setOnClickListener {
            binding.text1TextView.isEnabled = false
        }
    }
}