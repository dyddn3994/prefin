package com.prefin.ui.parentHome

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.prefin.databinding.ItemChildAccountBinding
import com.prefin.model.dto.ChildAccount

import com.prefin.util.AdapterUtil


class ChildAccountAdapter(var context: Context) : ListAdapter<ChildAccount, ChildAccountAdapter.ItemViewHolder>(AdapterUtil.diffUtilChildAccount) {

    inner class ItemViewHolder(var binding: ItemChildAccountBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: ChildAccount) {
            binding.apply {
                fragmentParentHomeChildAccountNameTextView.text = data.accountUserName
                fragmentParentHomeChildAccountMoneyTextView.text = data.accountMoney

                // 계좌 눌렀을 때 소비 내역으로 가야함
                fragmentParentHomeChildAccountLinearLayout.setOnClickListener {

                }
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemChildAccountBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }


    lateinit var itemClickListener : ItemClickListener
    interface ItemClickListener{
        fun onClick(view: View, position: Int, data: ChildAccount, checked : Boolean)
    }
}





