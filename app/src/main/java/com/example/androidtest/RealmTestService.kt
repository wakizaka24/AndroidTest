package com.example.androidtest

import android.text.TextUtils.concat
import android.util.Base64
import android.util.Log
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.EmbeddedRealmObject
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import java.lang.Exception
import java.util.Arrays

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
    var taskId: Int? = null
    var name: String = ""
    var finished: Boolean = false
}

object RealmTestService {
    private var localRealm: Realm? = null

    val realm: Realm
        get() {
            if (localRealm != null) {
                return localRealm!!
            }

            // TODO: キーの保存先を安全な場所に保存し、存在する場合はそのキーを使用する
            // TODO: 暗号化の使用文字を元に戻す
            //val randomCharList = "ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz0123456789";
            val randomCharList = "A"
            val key64byte: String = (1..64)
                .map { randomCharList.random() }
                .joinToString("");

            try {
                val config = RealmConfiguration.Builder(
                    schema = setOf(Dog::class, Person::class, Task::class))
                    .encryptionKey(key64byte.toByteArray()) // 暗号化キー
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
                                    taskId = 0
                                    name = "カレンダー作成"
                                    finished = false
                                },
                                Task().apply {
                                    taskId = 1
                                    name = "アプリ申請"
                                    finished = false
                                }
                            )
                        })
                    }.build()

                localRealm = Realm.open(config)
                Log.d("Realm情報", "開いたRealm ${localRealm!!.configuration.name}")
            } catch (e: Exception) {
                Log.d("Realm情報", "${e.message}")
            }

            return localRealm!!
        }
}