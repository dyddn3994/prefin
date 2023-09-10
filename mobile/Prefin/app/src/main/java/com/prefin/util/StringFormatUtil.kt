package com.prefin.util

import android.annotation.SuppressLint
import java.text.DecimalFormat
import java.text.SimpleDateFormat

class StringFormatUtil {
    companion object {
        @SuppressLint("SimpleDateFormat")
        fun dateToString(date: Long): String {
            return SimpleDateFormat("yyyy년 MM월 dd일 HH:mm").format(date)
        }

        fun moneyToWon(money: Int): String {
            return "${DecimalFormat("#,###").format(money)} 원"
        }
    }
}
