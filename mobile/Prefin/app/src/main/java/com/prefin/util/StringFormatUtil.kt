package com.prefin.util

import android.annotation.SuppressLint
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Locale

class StringFormatUtil {
    companion object {
        @SuppressLint("SimpleDateFormat")
        fun dateToString(date: Long): String {
            val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일 ", Locale.KOREA)
            return dateFormat.format(date)
        }

        fun dateTimeToString(date: Long): String {
            val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일 HH:mm", Locale.KOREA)
            return dateFormat.format(date)
        }

        fun moneyToWon(money: Int): String {
            return "${DecimalFormat("#,###").format(money)} 원"
        }
    }
}
