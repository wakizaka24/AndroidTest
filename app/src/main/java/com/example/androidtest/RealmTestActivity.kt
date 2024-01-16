package com.example.androidtest

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.androidtest.databinding.ActivityRealmTestBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RealmTestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRealmTestBinding
    private var realmTestService: RealmTestService = RealmTestService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRealmTestBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.gettingApiButton.stateListAnimator = null
        binding.gettingApiButton.setOnClickListener {
            realmTestService.getApiAccess().getTest1Table(3)
                .enqueue(object : Callback<RealmTestService.Api2Response> {
                    override fun onResponse(
                        call: Call<RealmTestService.Api2Response>,
                        response: Response<RealmTestService.Api2Response>
                    ) {
                        val colum2: String = response.body()?.data?.colum2 ?: ""
                        Log.d("RealmTestActivity", "colum2=${colum2}")
                        showMessage(response.body().toString())
                    }

                    override fun onFailure(
                        call: Call<RealmTestService.Api2Response>,
                        t: Throwable
                    ) {
                        Log.d("RealmTestActivity", t.message ?: "APIエラー")
                    }
                })
        }

        binding.postingApiButton.stateListAnimator = null
        binding.postingApiButton.setOnClickListener {
            val body = RealmTestService.Api3RequestBody(
                values = arrayOf(
                    RealmTestService.Api3RequestTable1Value(
                        3, "Value 33333!"
                    )
                ))
            realmTestService.getApiAccess().postTest1Table(body)
                .enqueue(object : Callback<Void> {
                    override fun onResponse(
                        call: Call<Void>,
                        response: Response<Void>
                    ) {
                        if (response.raw().code() == 200) {
                            showMessage("データの追加に成功しました")
                        } else {
                            showMessage(response.errorBody()?.string() ?: "APIエラー")
                        }
                    }

                    override fun onFailure(
                        call: Call<Void>,
                        t: Throwable
                    ) {
                        Log.d("RealmTestActivity", t.message ?: "APIエラー")
                    }
                })
        }

        binding.deletingApiButton.stateListAnimator = null
        binding.deletingApiButton.setOnClickListener {
            val body = RealmTestService.Api4RequestBody(
                keys = arrayOf(3)
            )
            realmTestService.getApiAccess().deleteTest1Table(body)
                .enqueue(object : Callback<Void> {
                    override fun onResponse(
                        call: Call<Void>,
                        response: Response<Void>
                    ) {
                        showMessage("データを削除しました")
                    }

                    override fun onFailure(
                        call: Call<Void>,
                        t: Throwable
                    ) {
                        Log.d("RealmTestActivity", t.message ?: "APIエラー")
                    }
                })

        }
    }

    fun showMessage(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Realmの検証")
            .setMessage(message)
            .setPositiveButton("OK") { _, _ ->

            }
        builder.create()
        builder.show()
    }
}