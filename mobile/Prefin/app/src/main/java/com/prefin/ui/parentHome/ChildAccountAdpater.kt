package com.prefin.ui.parentHome

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.prefin.databinding.ItemChildAccountBinding
import com.prefin.model.dto.Child
import com.prefin.util.AdapterUtil

class ChildAccountAdapter(var context: Context) : ListAdapter<Child, ChildAccountAdapter.ItemViewHolder>(AdapterUtil.diffUtilChildAccount) {

    inner class ItemViewHolder(var binding: ItemChildAccountBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Child) {
            binding.apply {
                fragmentParentHomeChildAccountNameTextView.text = "${data.name}의 계좌 "
                fragmentParentHomeChildAccountMoneyTextView.text = "${data.balance} 원"

                // 계좌 눌렀을 때 소비 내역으로 가야함
                fragmentParentHomeChildAccountLinearLayout.setOnClickListener {
                    itemClickListener.onClick(binding, layoutPosition, data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemChildAccountBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    lateinit var itemClickListener: ItemClickListener
    interface ItemClickListener {
        fun onClick(binding: ItemChildAccountBinding, position: Int, data: Child)
    }
}
