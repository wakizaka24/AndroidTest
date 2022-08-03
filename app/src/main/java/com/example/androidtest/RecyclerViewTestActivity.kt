package com.example.androidtest

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewTestActivity : AppCompatActivity(), TestRecyclerViewHolder.ItemClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view_test)

        // 1. Activityから再利用するViewの値を設定できるか。
        // ・初期値の設定は可能でした。

        val strings = listOf("水素", "窒素", "炭素", "酸素ああああああああああああああああああああああああああああ", "リン",
            "硫黄", "マグネシウム", "カルシウム", "ナトリウム", "カリウム",
            "ストロンチウム", "バリウム", "鉄", "亜鉛", "銅", "鉛", "銀",
            "金", "白金", "水銀")

        viewAdapter = TestRecyclerAdapter(this, this, strings)
        viewManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        recyclerView = findViewById<RecyclerView>(R.id.testRecyclerView).apply {
            // サイズ変更を行わない
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    // 2. 再利用するViewのイベントをActivityで受け取れるか。
    // ・セルの選択はイベントの取得が可能でした。

    override fun onItemClick(view: View, position: Int) {
        Toast.makeText(applicationContext, "tapped position = $position",
            Toast.LENGTH_SHORT).show()
    }
}