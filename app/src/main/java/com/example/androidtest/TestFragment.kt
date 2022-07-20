package com.example.androidtest

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class TestFragment : Fragment() {
    interface OnButtonClickListener {
        fun onTestFragmentViewCreated()
        fun onTestFragmentButtonClicked()
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_test, container,
            false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        args ?: return

        val stringArg = args.getString("string_arg")
        //val intArg = args.getInt("int_arg")

        val listener = context as? OnButtonClickListener
        listener?.onTestFragmentViewCreated()

        val textView = view.findViewById<TextView>(R.id.textView1)
        textView.setText(stringArg)
        view.findViewById<Button>(R.id.button1).setOnClickListener {
            listener?.onTestFragmentButtonClicked()
        }
    }
}
