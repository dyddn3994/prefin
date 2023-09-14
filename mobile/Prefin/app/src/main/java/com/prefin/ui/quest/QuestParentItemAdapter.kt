package com.prefin.ui.quest

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
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

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val quest = currentList[position]
                    itemClickListener.onClick(it, adapterPosition, quest)
                }
            }
        }
        fun bind(data: Quest) = with(binding) {
            itemParentQuestItemQuestNameTextView.text = data.title
            itemParentQuestItemAmountTextView.text = StringFormatUtil.moneyToWon(data.reward)

            // 등록되어 있을 경우 클릭 불가
            if (data.registered) {
                // 등록 상태
                root.setBackgroundColor(Color.GRAY)
                root.isClickable = false
            } else {
                // 미등록 상태
                root.setBackgroundColor(Color.WHITE)
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


    lateinit var itemClickListener : ItemClickListener
    interface ItemClickListener{
        fun onClick(view: View, position: Int, data: Quest)
    }
}
