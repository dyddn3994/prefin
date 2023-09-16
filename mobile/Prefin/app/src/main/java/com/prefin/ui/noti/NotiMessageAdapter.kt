package com.prefin.ui.noti

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import com.prefin.config.ApplicationClass
import com.prefin.databinding.NotiCardViewBinding
import com.prefin.model.local.NotiMessage
import com.prefin.util.AdapterUtil
import com.prefin.util.StringFormatUtil
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NotiMessageAdapter (var context : Context,var fragment: NotiFragment) : ListAdapter<NotiMessage, NotiMessageAdapter.ItemViewHolder>(
    AdapterUtil.diffUtilNotiMessage
) {
    // 스와이프한 아이템 삭제
    fun removeItem(position: Int) {
        if (position in 0 until itemCount) {
            val newList = ArrayList(currentList)
            (fragment as NotiFragment).deleteMessage(currentList[position])
            newList.removeAt(position)
            submitList(newList)
        }
    }

    inner class ItemViewHolder(var binding : NotiCardViewBinding) :  RecyclerView.ViewHolder(binding.root) {

        fun bind(data : NotiMessage){
            
            binding.notiTitle.text = data.title
            binding.notiCardRegisterTime.text = StringFormatUtil.dateTimeToString(data.receiveTime)
            binding.notiBody.text = data.body


        }

    }



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotiMessageAdapter.ItemViewHolder {
        return ItemViewHolder(
            NotiCardViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NotiMessageAdapter.ItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }


}