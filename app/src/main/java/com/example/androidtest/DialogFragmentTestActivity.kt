package com.example.androidtest

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment

class DialogFragmentTestActivity : AppCompatActivity(), View.OnClickListener,
    DialogFragmentTestFragment.DialogListener {
    private lateinit var displayButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog_fragment_test)

        // 1. DialogFragmentを利用してダイアログを表示できるか。
        // ・可能でした。
        displayButton = findViewById(R.id.displayButton)
        displayButton.setOnClickListener(this)
    }

    override fun onClick(view: View){
        when (view.id) {
            R.id.displayButton -> {
                val dialogFragment = DialogFragmentTestFragment()
                dialogFragment.listener = this
                dialogFragment.show(supportFragmentManager, "dialog_fragment_test_dialog")
            }
        }
    }

    // 2. DialogFragmentからActivityへ入力内容を共有できるか。
    // ・可能でした。
    override fun onDialogPositive(dialog: DialogFragment) {
        Log.d("Dialog表示(Activity)", "OKボタン押下")
    }

    override fun onDialogNegative(dialog: DialogFragment) {
        Log.d("Dialog表示(Activity)", "キャンセルボタン押下")
    }

    override fun onDialogNeutral(dialog: DialogFragment) {
        Log.d("Dialog表示(Activity)", "あとでボタン押下")
    }
}
