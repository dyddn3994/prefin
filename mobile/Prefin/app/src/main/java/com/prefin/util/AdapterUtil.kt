package com.prefin.util

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.recyclerview.widget.DiffUtil

/**
 *         // diffUtil: currentList에 있는 각 아이템들을 비교하여 최신 상태를 유지하도록 한다.
 *         예시
       val diffUtilUserInfo = object : DiffUtil.ItemCallback<Member>() {
            override fun areItemsTheSame(oldItem: Member, newItem: Member): Boolean {
               return oldItem.id == newItem.id
          }

          override fun areContentsTheSame(oldItem: Member, newItem: Member): Boolean {
              return oldItem == newItem
          }
      }

 *
 * */
class AdapterUtil {

    companion object {



    }
}