package com.prefin.ui.quest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.prefin.databinding.ItemChildQuestBinding
import com.prefin.model.dto.QuestOwned
import com.prefin.util.AdapterUtil
import com.prefin.util.StringFormatUtil

class QuestChildAdapter(var context: Context) : ListAdapter<QuestOwned, QuestChildAdapter.ItemViewHolder>(
    AdapterUtil.diffUtilQuestOwned,
) {
    inner class ItemViewHolder(var binding: ItemChildQuestBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: QuestOwned) = with(binding) {
            itemChildQuestQuestNameTextView.text = data.quest.title
            itemChildQuestAmountTextView.text = StringFormatUtil.moneyToWon(data.quest.reward)

            // 퀘스트 완료 요청된 상태일 경우 시계 표시
            if (data.requested) {
                // 요청 상태
                itemChildQuestTimeImageView.visibility = View.VISIBLE
            } else {
                // 미요청 상태
                itemChildQuestTimeImageView.visibility = View.GONE
            }
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
