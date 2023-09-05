package com.prefin.ui.quest

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.prefin.databinding.ItemChildQuestBinding
import com.prefin.model.dto.Quest
import com.prefin.util.AdapterUtil
import com.prefin.util.StringFormatUtil

class QuestChildAdapter(var context: Context) : ListAdapter<Quest, QuestChildAdapter.ItemViewHolder>(
    AdapterUtil.diffUtilQuest,
) {
    inner class ItemViewHolder(var binding: ItemChildQuestBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Quest) = with(binding) {
            itemChildQuestQuestNameTextView.text = data.title
            itemChildQuestAmountTextView.text = StringFormatUtil.moneyToWon(data.reward)

            // TODO: 요청한 상태일 경우 시계 표시
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemChildQuestBinding.inflate(
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
