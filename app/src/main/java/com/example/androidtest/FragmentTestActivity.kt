package com.example.androidtest

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentContainerView

class FragmentTestActivity : AppCompatActivity(), TestFragment.OnButtonClickListener {
    lateinit var fragmentContainerView: FragmentContainerView
    lateinit var text: TextView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_test)

        /*
            4. ActivityからFragmentの呼び出しにパラメーターを追加できるか。
            ・可能でした。
         */

        val fragment = TestFragment()
        val args = Bundle()
        args.putString("string_arg", "Initial text.")
        //args.putInt("int_arg", 33)
        fragment.setArguments(args)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragmentContainerView, fragment)
        transaction.commit()

        fragmentContainerView = findViewById(R.id.fragmentContainerView)
        findViewById<Button>(R.id.button1).setOnClickListener {
            text.setText("Activity event.")
        }
    }

    /*
        3. ActivityからのイベントをFragmentで受け取れるか。
        ・可能でした。
     */

    override fun onTestFragmentViewCreated() {
        text = fragmentContainerView.findViewById(R.id.textView1)
    }

    /*
        2. Activityに配置したFragmentのイベントをActivityで受け取れるか。
        ・可能でした。
     */

    @SuppressLint("SetTextI18n")
    override fun onTestFragmentButtonClicked() {
        text.setText("Fragment event.")
    }
}
