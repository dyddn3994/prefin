package com.prefin.ui.quest

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.prefin.databinding.ItemParentQuestBinding
import com.prefin.model.dto.Quest
import com.prefin.util.AdapterUtil
import com.prefin.util.StringFormatUtil

class QuestParentAdapter(var context: Context) : ListAdapter<Quest, QuestParentAdapter.ItemViewHolder>(
    AdapterUtil.diffUtilQuest,
) {
    inner class ItemViewHolder(var binding: ItemParentQuestBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Quest) = with(binding) {
            itemParentQuestQuestNameTextView.text = data.title
            itemParentQuestDateTextView.text = StringFormatUtil.dateToString(data.endDate)
            itemParentQuestAmountTextView.text = StringFormatUtil.moneyToWon(data.reward)

            // TODO: 요청한 상태일 경우 시계 표시
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemParentQuestBinding.inflate(
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
