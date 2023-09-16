package com.prefin.ui.saving

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.prefin.R
import com.prefin.databinding.ItemSavingHistoryBinding
import com.prefin.model.dto.SavingHistory
import com.prefin.util.AdapterUtil
import com.prefin.util.StringFormatUtil

class SavingHistoryAdapter(var context: Context) : ListAdapter<SavingHistory, SavingHistoryAdapter.ItemViewHolder>(
    AdapterUtil.diffUtilSavingHistory,
) {
    inner class ItemViewHolder(var binding: ItemSavingHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: SavingHistory) = with(binding) {
            when (data.savingType) {
                "DEPOSIT" -> {
                    itemSavingHistoryTypeTextView.text = "저축"
                    itemSavingHistoryAmountTextView.setTextColor(context.resources.getColor(R.color.colorPrimary))
                }
                "WITHDRAW" -> {
                    itemSavingHistoryTypeTextView.text = "출금"
                    itemSavingHistoryAmountTextView.setTextColor(context.resources.getColor(R.color.colorRed))
                }
                else -> {
                    itemSavingHistoryTypeTextView.text = "이자"
                    itemSavingHistoryAmountTextView.setTextColor(context.resources.getColor(R.color.green))
                }
            }
            itemSavingHistoryDateTextView.text = StringFormatUtil.dateTimeToString(data.savingDate)
            itemSavingHistoryAmountTextView.text = data.savingAmount.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemSavingHistoryBinding.inflate(
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
