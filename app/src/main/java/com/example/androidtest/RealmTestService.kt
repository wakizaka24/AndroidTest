package com.example.androidtest

import android.util.Log
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.dynamic.DynamicMutableRealmObject
import io.realm.kotlin.dynamic.DynamicRealmObject
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.migration.AutomaticSchemaMigration
import io.realm.kotlin.types.EmbeddedRealmObject
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import java.lang.Exception

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

val migration = AutomaticSchemaMigration { context ->
    val oldVersion = context.oldRealm.schemaVersion()
    // val schema = context.newRealm.schema()

    if (oldVersion < 2) {
        context.enumerate(className = "Person") {
            oldObject: DynamicRealmObject, newObject: DynamicMutableRealmObject? ->
            newObject?.run {
                /*
                // カラムの型変換の例
                set(
                    "deviceId",
                    oldObject.getValue<ObjectId>(fieldName = "deviceId").toString()
                )
                // カラムのマージの例
                set(
                    "fullName",
                    "${oldObject.getValue<String>(fieldName = "firstName")}" +
                            "${oldObject.getValue<String>(fieldName = "lastName")}"
                )
                // カラムのリネームの例
                set(
                    "yearsSinceBirth",
                    oldObject.getValue<String>(fieldName = "age")
                )
                */
            }
        }
    }
}

object RealmTestService {
    private var localRealm: Realm? = null
    private var currentSchemaVersion: Long = 1

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
                    // .deleteRealmIfMigrationNeeded() // マイグレーション不要
                    .schemaVersion(currentSchemaVersion) // スキーマバージョン
                    .migration(migration) // マイグレーション設定
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