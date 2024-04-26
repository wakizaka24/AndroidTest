package com.example.androidtest

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.androidtest.databinding.ActivityRealmTestBinding
import io.realm.kotlin.ext.query
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlin.math.log

class RealmTestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRealmTestBinding

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRealmTestBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val dogResult = RealmTestService.realm.query<Dog>(query = "dogId == $0", args = arrayOf(2))
            .find()
        var dogName = "いない"
        if (dogResult.size > 0) {
            dogName = dogResult.first().name
        }

        val personNum = RealmTestService.realm.query<Person>().find().size
        Log.d("Realm情報", "抽出したDog名: $dogName 初期のPersonの数: $personNum")
        logOfRealm()
        // showMessage("抽出したDog名: $dogName 初期のPersonの数: $personNum")

        binding.addPerson.stateListAnimator = null
        binding.addPerson.setOnClickListener {
            RealmTestService.realm.writeBlocking {
                val personResult = this.query<Person>(
                    query = "personId == $0", args = arrayOf(2)).find()
                if (personResult.size == 0) {
                    copyToRealm(Person().apply {
                        personId = 2
                        name = "追加した人"
                        age = 30
                    })
                }
            }
            logOfRealm()
        }

        binding.updatePerson.stateListAnimator = null
        binding.updatePerson.setOnClickListener {
            RealmTestService.realm.writeBlocking {
                val personResult = this.query<Person>(
                    query = "personId == $0", args = arrayOf(2)).find()
                if (personResult.size > 0) {
                    personResult.first().name = "更新した人"
                }
            }
            logOfRealm()
        }

        binding.deletePerson.stateListAnimator = null
        binding.deletePerson.setOnClickListener {
            RealmTestService.realm.writeBlocking {
                val personResult = this.query<Person>(
                    query = "personId == $0", args = arrayOf(2)).find()
                if (personResult.size > 0) {
                    delete(personResult.first())
                }
            }
            logOfRealm()
        }

        binding.addPersonTask.stateListAnimator = null
        binding.addPersonTask.setOnClickListener {
            RealmTestService.realm.writeBlocking {
                val personResult = this.query<Person>(
                    query = "personId == $0", args = arrayOf(1)).find()
                var taskId = 0
                if (personResult.first().tasks.size > 0) {
                    val maxTask = personResult.first().tasks.maxBy { it.taskId!! }
                    taskId = maxTask.taskId!! + 1
                }
                personResult.first().tasks.add(Task().apply {
                    this.taskId = taskId
                    name = "追加したタスク"
                    finished = false
                })
            }
            logOfRealm()
        }

        binding.updatePersonTask.stateListAnimator = null
        binding.updatePersonTask.setOnClickListener {
            RealmTestService.realm.writeBlocking {
                val personResult = this.query<Person>(
                    query = "personId == $0", args = arrayOf(1)).find()
                if (personResult.size > 0 && personResult.first().tasks.size > 0) {
                    val minTask = personResult.first().tasks.minBy { it.taskId!! }
                    minTask.finished = true
                }
            }
            logOfRealm()
        }

        binding.deletePersonTask.stateListAnimator = null
        binding.deletePersonTask.setOnClickListener {
            RealmTestService.realm.writeBlocking {
                val personResult = query<Person>(
                    query = "personId == $0", args = arrayOf(1)).find()
                if (personResult.size > 0) {
                    val tasks = personResult.first().tasks.filter { it.finished }
                    personResult.first().tasks.removeAll(tasks)
                }
            }
            logOfRealm()
        }
    }

//    private fun showMessage(message: String) {
//        val builder = AlertDialog.Builder(this)
//        builder.setTitle("Realmの検証")
//            .setMessage(message)
//            .setPositiveButton("OK") { _, _ ->
//            }
//        builder.create()
//        builder.show()
//    }

    private fun logOfRealm() {
        val parsonResult = RealmTestService.realm.query<Person>().find()
        Log.d("Realm情報", "---Person---")
        for (i in 0 ..< parsonResult.size) {
            val parson = parsonResult[i]
            Log.d("Realm情報",
                "${parson.personId} ${parson.name} ${parson.age}")
            if (parson.tasks.size > 0) {
                Log.d("Realm情報", "---Person>Task---")
            }
            for (j in 0..<parson.tasks.size) {
                val task = parson.tasks[j]
                Log.d("Realm情報",
                    "${task.taskId} ${task.name} ${task.finished}")
            }
        }

        Log.d("Realm情報", "---Dog---")
        val dogResult = RealmTestService.realm.query<Dog>().find()
        for(i in 0 ..< dogResult.size) {
            val dog = dogResult[i]
            Log.d("Realm情報",
                "${dog.dogId} ${dog.name} ${dog.age}")
        }
    }
}