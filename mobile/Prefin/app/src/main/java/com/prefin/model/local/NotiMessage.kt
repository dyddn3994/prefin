package com.prefin.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "NotiMessage")
data class NotiMessage(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val title : String,
    val body : String,
    val receiveTime : Long,

)
