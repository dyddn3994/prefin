package com.prefin.ui.quest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.prefin.databinding.ItemParentQuestBinding
import com.prefin.model.dto.Quest
import com.prefin.model.dto.QuestOwned
import com.prefin.model.dto.QuestOwnedQuest
import com.prefin.util.AdapterUtil
import com.prefin.util.StringFormatUtil

class QuestParentAdapter(var context: Context) : ListAdapter<QuestOwnedQuest, QuestParentAdapter.ItemViewHolder>(
    AdapterUtil.diffUtilQuestOwnedQuest,
) {
    inner class ItemViewHolder(var binding: ItemParentQuestBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val questowned = currentList[position]
                    itemClickListener.onClick(it, adapterPosition, questowned)
                }
            }
        }
        fun bind(data: QuestOwnedQuest) = with(binding) {
            itemParentQuestQuestNameTextView.text = data.title
            itemParentQuestDateTextView.text = StringFormatUtil.dateToString(data.endDate)
            itemParentQuestAmountTextView.text = StringFormatUtil.moneyToWon(data.reward)
            // 퀘스트 완료 요청된 상태일 경우 시계 표시
            if (data.requested) {
                // 요청 상태
                itemParentQuestTimeImageView.visibility = View.VISIBLE
            } else {
                // 미요청 상태
                itemParentQuestTimeImageView.visibility = View.GONE
            }
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

    lateinit var itemClickListener : ItemClickListener
    interface ItemClickListener{
        fun onClick(view: View, position: Int, data: QuestOwnedQuest)
    }
}
