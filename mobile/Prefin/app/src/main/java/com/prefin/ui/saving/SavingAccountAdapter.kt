package com.prefin.ui.saving

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.prefin.databinding.ItemChildAccountBinding
import com.prefin.model.dto.Child
import com.prefin.ui.parentHome.ChildAccountAdapter
import com.prefin.util.AdapterUtil

class SavingAccountAdapter(var context: Context) : ListAdapter<Child, SavingAccountAdapter.ItemViewHolder>(
    AdapterUtil.diffUtilChildAccount) {
    inner class ItemViewHolder(var binding: ItemChildAccountBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Child) {
            binding.apply {
                fragmentParentHomeChildAccountNameTextView.text = "${data.name}의 저축 계좌 "
                fragmentParentHomeChildAccountMoneyTextView.text = "${data.savingAmount} 원"

                // 계좌 눌렀을 때 소비 내역으로 가야함
                fragmentParentHomeChildAccountLinearLayout.setOnClickListener {

                }
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavingAccountAdapter.ItemViewHolder {
        return ItemViewHolder(
            ItemChildAccountBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SavingAccountAdapter.ItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }


    lateinit var itemClickListener : ItemClickListener
    interface ItemClickListener{
        fun onClick(view: View, position: Int, data: Child, checked : Boolean)
    }
}