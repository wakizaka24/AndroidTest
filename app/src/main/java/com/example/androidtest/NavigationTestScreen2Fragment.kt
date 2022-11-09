package com.example.androidtest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class NavigationTestScreen2Fragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        val view = inflater.inflate(R.layout.fragment_navigation_test_screen2, container,
            false)

        view.findViewById<Button>(R.id.button1).setOnClickListener {
            // 2. Navigationを利用して画面遷移後に元の画面に戻ることができるか。
            // ・可能でした。
            val action = NavigationTestScreen2FragmentDirections.next2("遷移先のパラメーター")
            findNavController().navigate(action)
        }

        val name = arguments?.getString("name")
        name?.run {
            val textView = view.findViewById<TextView>(R.id.textView1)
            textView.text = name
        }

        return view
    }
}