package com.example.androidtest

import android.util.Log
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.EmbeddedRealmObject
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class Dog: RealmObject {
    @PrimaryKey
    var dogId: Int? = null
    var name: String = ""
    var age: Int = 0
}

class Person: RealmObject {
    @PrimaryKey
    var personId: Int? = null
    var name: String = ""
    var age: Int = 0
    var tasks: RealmList<Task> = realmListOf()
}

class Task: EmbeddedRealmObject {
    var name: String = ""
    var finished: Boolean = false
}

object RealmTestService {
    var realm: Realm
    init {
        val config = RealmConfiguration.Builder(
            schema = setOf(Dog::class, Person::class, Task::class))
            // 64バイトのキーを動的に作成する必要あり
            // キーの保存先も安全な場所に保存する必要あり
            // .encryptionKey(Base64.decode("abc...", Base64.DEFAULT)) // 暗号化キー
            .initialData {// DBの初期値
                copyToRealm(Dog().apply {
                    dogId = 0
                    name = "いぬ"
                    age = 3
                })
                copyToRealm(Dog().apply {
                    dogId = 1
                    name = "うさぎ"
                    age = 2
                })
                copyToRealm(Dog().apply {
                    dogId = 2
                    name = "とり"
                    age = 2
                })
                copyToRealm(Person().apply {
                    personId = 0
                    name = "りょうたくん"
                    age = 20
                })
                copyToRealm(Person().apply {
                    personId = 1
                    name = "新たなりょうたくん"
                    age = 2
                    tasks = realmListOf(
                        Task().apply {
                            name = "カレンダー作成"
                            finished = false
                        },
                        Task().apply {
                            name = "アプリ申請"
                            finished = false
                        }
                    )
                })
            }.build()

        realm = Realm.open(config)
        Log.d("Realm情報", "開いたRealm ${realm.configuration.name}")
    }
}