package com.example.androidtest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androidtest.databinding.ActivityButtonTestBinding

class ButtonTestActivity : AppCompatActivity() {
    lateinit var binding: ActivityButtonTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityButtonTestBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // 5. Buttonの押下時のイベントを登録できるか。
        // ・可能でした。
        binding.okButtonSwitchButton.setOnClickListener {
            binding.okButton.isEnabled = !binding.okButton.isEnabled
        }

        binding.okButtonEnableButton.setOnClickListener {
            binding.okButton.isEnabled = true
        }

        binding.okButtonDisableButton.setOnClickListener {
            binding.okButton.isEnabled = false
        }
    }
}