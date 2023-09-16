package com.prefin.ui.saving

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.prefin.databinding.ItemChildSelectBinding
import com.prefin.model.dto.Child
import com.prefin.util.AdapterUtil

class ChildSelectAdapter(var context: Context) : ListAdapter<Child, ChildSelectAdapter.ItemViewHolder>(
    AdapterUtil.diffUtilChildSelect,
) {
    inner class ItemViewHolder(var binding: ItemChildSelectBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Child) = with(binding) {
            itemChildSelectNameTextView.text = data.name
            root.setOnClickListener {
                itemClickListener.onClick(binding, layoutPosition, data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildSelectAdapter.ItemViewHolder {
        return ItemViewHolder(
            ItemChildSelectBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )
    }

    override fun onBindViewHolder(holder: ChildSelectAdapter.ItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    lateinit var itemClickListener: ItemClickListener
    interface ItemClickListener {
        fun onClick(binding: ItemChildSelectBinding, position: Int, data: Child)
    }
}
