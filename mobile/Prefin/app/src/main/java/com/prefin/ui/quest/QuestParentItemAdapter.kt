package com.prefin.ui.quest

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.prefin.R
import com.prefin.databinding.ItemParentQuestItemBinding
import com.prefin.model.dto.Quest
import com.prefin.util.AdapterUtil
import com.prefin.util.StringFormatUtil

class QuestParentItemAdapter(var context: Context) : ListAdapter<Quest, QuestParentItemAdapter.ItemViewHolder>(
    AdapterUtil.diffUtilQuest,
) {
    inner class ItemViewHolder(var binding: ItemParentQuestItemBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ResourceAsColor")
        fun bind(data: Quest) = with(binding) {
            itemParentQuestItemQuestNameTextView.text = data.title
            itemParentQuestItemAmountTextView.text = StringFormatUtil.moneyToWon(data.reward)

            // 등록되어 있을 경우 클릭 불가
            if (data.registered) {
                // 등록 상태
                root.setBackgroundColor(R.color.colorGray)
                root.isClickable = false
            } else {
                // 미등록 상태
                root.setBackgroundColor(R.color.white)
                root.isClickable = true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemParentQuestItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
    
    
}
