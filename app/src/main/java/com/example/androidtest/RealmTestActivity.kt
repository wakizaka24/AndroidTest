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



        RealmTestService.realm.writeBlocking {
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
        }

        val dog = RealmTestService.realm.query<Dog>(query = "dogId == $0", args = arrayOf(2))
            .find().first()
        val personNum = RealmTestService.realm.query<Person>()
            .find().size

        showMessage("初期のDog: ${dog.name} 初期のPersonの数: $personNum")
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