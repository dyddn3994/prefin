package com.prefin.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [NotiMessage::class], version = 1)
abstract class NotiMessageDataBase : RoomDatabase() {

    abstract val notiMessageDao : NotiMessageDAO

    companion object {
        private var instance: NotiMessageDataBase? = null

        @Synchronized
        fun getInstance(context: Context): NotiMessageDataBase? {
            if (instance == null)
                synchronized(NotiMessageDataBase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NotiMessageDataBase::class.java,
                        "noti.db"
                    )
                        .build()
                }
            return instance
        }

        fun destroyInstance() {
            instance = null
        }
    }
}