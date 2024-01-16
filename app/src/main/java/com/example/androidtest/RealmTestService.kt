package com.example.androidtest

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.types.RealmObject
import retrofit2.Retrofit
import retrofit2.Call
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.io.Serializable
import java.util.Date

class RealmTestService {
    interface APIInterface {
        @GET("api2_get_test1_table.php")
        fun getTest1Table(@Query("colum1") colum1: Int?): Call<Api2Response>
        @POST("api3_post_test1_table.php")
        fun postTest1Table(@Body body: Api3RequestBody): Call<Void>
        // GoogleではDeleteメソッドにBodyを持たせるとエラーになる。
        // DELETEはパスにIDを持たせるらしい。
        // @DELETE("api4_delete_test1_table.php")
        // ただ、RFCでは特に禁止していないので持たせる場合は以下の対応を行う。
        // RFC section 9: Method Definitions
        @HTTP(method = "DELETE", path = "api4_delete_test1_table.php", hasBody = true)
        fun deleteTest1Table(@Body body: Api4RequestBody): Call<Void>
    }

    data class Api2Response(
            @SerializedName("data") val data: Api2ResponseData
        ): Serializable
    data class Api2ResponseData(
            @SerializedName("COLUM1") val colum1: Int,
            @SerializedName("COLUM2") val colum2: String
        ): Serializable

    data class Api3RequestBody(
            @SerializedName("table1_values") val values:
            Array<Api3RequestTable1Value>
        ): Serializable {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Api3RequestBody

            if (!values.contentEquals(other.values)) return false

            return true
        }

        override fun hashCode(): Int {
            return values.contentHashCode()
        }
    }
    data class Api3RequestTable1Value(
        @SerializedName("COLUM1") val colum1: Int,
        @SerializedName("COLUM2") val colum2: String
    ): Serializable

    data class Api4RequestBody(
        @SerializedName("table1_deleting_keys") val keys:
        Array<Int>
    ): Serializable {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Api4RequestBody

            if (!keys.contentEquals(other.keys)) return false

            return true
        }

        override fun hashCode(): Int {
            return keys.contentHashCode()
        }
    }

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
        apiInterfaceImpl = retrofit.create(APIInterface::class.java)
    }

    fun getApiAccess(): APIInterface {
        return apiInterfaceImpl
    }

    class Event : RealmObject {
        var id: String? = null
        var title: String? = null
        var startDate: Date? = null
        var endDate: Date? = null
    }

    fun getEventList(id: String?): List<Event> {
        // 領域を開く
        val config = RealmConfiguration.create(schema = setOf(Event::class))
        val realm: Realm = Realm.open(config)

        val eventList: RealmResults<Event> = realm.query<Event>("id == $id").find()
        return eventList.toList()
    }

    fun addEvent(event: Event) {
        
    }
}