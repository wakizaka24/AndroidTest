package com.example.androidtest

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import retrofit2.Retrofit
import retrofit2.Call
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.Serializable

class Retrofit2TestService {
    interface APIInterface {
        @GET("api2_get_test1_table.php")
        fun getTest1Table(@Query("colum1") colum1: Int?): Call<Api2Response>
    }
    data class Api2Response (
        @SerializedName("data") val data: Api2ResponseData
        ): Serializable
    data class Api2ResponseData (
            @SerializedName("COLUM1") val colum1: Int,
            @SerializedName("COLUM2") val colum2: String
        ): Serializable

    companion object {
        private const val BASE_URL = "https://wakizaka24.sakura.ne.jp/reversi/php/"
    }

    private var apiInterfaceImpl: APIInterface

    init {
        val gsonFactory = GsonConverterFactory.create(
            GsonBuilder().serializeNulls().create())
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(gsonFactory)
            .build()
        apiInterfaceImpl = retrofit.create(APIInterface::class.java);
    }

    fun getApiAccess(): APIInterface {
        return apiInterfaceImpl;
    }
}