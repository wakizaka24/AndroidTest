package com.example.androidtest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class IntentTestActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        const val CHILDREN_STATES = "children_states"
    }

    private lateinit var moveButton: Button
    private var childrenStates: Boolean = false

    fun createIntent(context: Context, childrenStates: Boolean): Intent {
        // 3. Intentを利用して画面遷移時にパラメーターを渡すことができるか。
        // ・可能でした。
        val intent = Intent(
            context,
            IntentTestActivity::class.java
        ).apply {
            putExtra(CHILDREN_STATES, childrenStates)
        }

        return intent
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intent_test)

        childrenStates = intent.getBooleanExtra(
            CHILDREN_STATES, false
        )

        moveButton = findViewById<Button>(R.id.moveButton).apply {
            if (!childrenStates) {
                text = "画面遷移"
            } else {
                text = "戻る"
            }
        }

        moveButton.setOnClickListener(this)
    }

    override fun onClick(view: View){
        when (view.id) {
            R.id.moveButton -> {
                if (!childrenStates) {
                    // 1. Intentを利用して画面遷移ができるか。
                    // ・可能でした。
                    startActivity(createIntent(this, true))
                } else {
                    // 2. Intentを利用して画面遷移後に元の画面に戻ることができるか。
                    // ・可能でした。
                    finish()
                }
            }
        }
    }
}
