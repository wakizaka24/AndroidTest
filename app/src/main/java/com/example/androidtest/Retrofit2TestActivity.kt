package com.example.androidtest

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.androidtest.databinding.ActivityRetrofit2TestBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Retrofit2TestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRetrofit2TestBinding
    private var retrofit2TestService: Retrofit2TestService = Retrofit2TestService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRetrofit2TestBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.gettingApiButton.stateListAnimator = null;
        binding.gettingApiButton.setOnClickListener {
            retrofit2TestService.getApiAccess().getTest1Table(3)
                .enqueue(object : Callback<Retrofit2TestService.Api2Response> {
                    override fun onResponse(
                        call: Call<Retrofit2TestService.Api2Response>,
                        response: Response<Retrofit2TestService.Api2Response>
                    ) {
                        val colum2: String = response.body()?.data?.colum2 ?: ""
                        Log.d("Retrofit2TestActivity", "colum2=${colum2}")
                        showMessage(response.body().toString())
                    }

                    override fun onFailure(
                        call: Call<Retrofit2TestService.Api2Response>,
                        t: Throwable
                    ) {
                    }
                })
        }
    }

    fun showMessage(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Retrofit2の検証")
            .setMessage(message)
            .setPositiveButton("OK") { _, _ ->

            }
        builder.create()
        builder.show()
    }
}