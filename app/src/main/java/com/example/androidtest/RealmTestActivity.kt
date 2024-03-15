package com.example.androidtest

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.androidtest.databinding.ActivityRealmTestBinding
import io.realm.kotlin.ext.query

class RealmTestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRealmTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRealmTestBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        /*
        Personの追加(personId=1, エラーハンドリング含む)
        Personの更新(personId=1, 年齢の更新21歳にする)
        Personの削除(personId=1)
        Personのtaskの追加(personId=1, タスクIDを最大+1)
        Personのtaskの更新(personId=1, タスクIDの最小のレコードをfinished=trueにする)
        Personのtaskの削除(personId=1 and finished=true)
         */


//        RealmTestService.realm.writeBlocking {
//            copyToRealm(Dog().apply {
//                dogId = 0
//                name = "いぬ"
//                age = 3
//            })
//            copyToRealm(Dog().apply {
//                dogId = 1
//                name = "うさぎ"
//                age = 2
//            })
//            copyToRealm(Dog().apply {
//                dogId = 2
//                name = "とり"
//                age = 2
//            })
//        }

        val dogResult = RealmTestService.realm.query<Dog>(query = "dogId == $0", args = arrayOf(2))
            .find()
        var dogName = "いない"
        if (dogResult.size > 0) {
            dogName = dogResult.first().name
        }

        val personNum = RealmTestService.realm.query<Person>().find().size
        showMessage("初期のDog: $dogName 初期のPersonの数: $personNum")

//        val tasks = RealmTestService.realm.query<Dog>().find()
//        CoroutineScope(newSingleThreadContext("RealmMonitoring")).launch {
//            tasks.asFlow().collect {
//                when (it) {
//                    is InitialResults -> {
//                        showMessage("Dog初期化件数: ${it.list.size}")
//                    }
//                    is UpdatedResults -> {
//                        showMessage("Dog更新件数: ${it.list.size}")
//                    }
//                }
//            }
//        }
    }

    private fun showMessage(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Realmの検証")
            .setMessage(message)
            .setPositiveButton("OK") { _, _ ->
            }
        builder.create()
        builder.show()
    }
}