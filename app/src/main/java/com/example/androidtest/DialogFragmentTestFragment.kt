package com.example.androidtest

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment

class DialogFragmentTestFragment: DialogFragment() {
    interface DialogListener {
        fun onDialogPositive(dialog: DialogFragment)
        fun onDialogNegative(dialog: DialogFragment)
        fun onDialogNeutral(dialog: DialogFragment)
    }

    var listener: DialogListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("タイトル！")
            .setMessage("ダイアログのメッセージです。")
            .setPositiveButton("OK") { _, _ ->
                listener?.onDialogPositive(this)
                Log.d("Dialog表示", "OKボタン押下")
            }
            .setNegativeButton("キャンセル") { _, _ ->
                listener?.onDialogNegative(this)
                Log.d("Dialog表示", "キャンセルボタン押下")
            }
            .setNeutralButton("あとで") { _, _ ->
                listener?.onDialogNeutral(this)
                Log.d("Dialog表示", "あとでボタン押下")
            }

        return builder.create()
    }

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        try {
//            listener = context as DialogListener
//        }catch (e: Exception){
//            Log.e("Dialog表示","ダイアログの表示に失敗しました。")
//        }
//    }
//
//    override fun onDetach() {
//        super.onDetach()
//        listener = null
//    }
}