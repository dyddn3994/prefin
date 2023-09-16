package com.prefin.model.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NotiMessageDAO {

    @Query("SELECT * FROM NotiMessage")
    fun getAll(): List<NotiMessage>


    // bookmark 저장 - 중복 값 충돌 발생 시 새로 들어온 데이터로 교체.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveNotiMessage(notiMessage: NotiMessage)

    // bookmark 삭제
    @Delete
    fun deleteNotiMessage(notiMessage: NotiMessage)
}