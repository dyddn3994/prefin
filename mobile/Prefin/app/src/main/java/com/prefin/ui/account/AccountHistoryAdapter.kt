package com.prefin.ui.account

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.prefin.R
import com.prefin.databinding.ItemLoanHistoryBinding
import com.prefin.model.dto.AccountHistory
import com.prefin.util.AdapterUtil

class AccountHistoryAdapter(var context: Context) : ListAdapter<AccountHistory, AccountHistoryAdapter.ItemViewHolder>(
    AdapterUtil.diffUtilAccountHistory,
) {
    inner class ItemViewHolder(var binding: ItemLoanHistoryBinding) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n", "ResourceAsColor")
        fun bind(data: AccountHistory) = with(binding) {
            itemLoanHistoryNameTextView.text = data.briefs
            itemLoanHistoryDateTextView.text = "" +
                "${data.transactionDate.substring(0..3)}년 " +
                "${data.transactionDate.substring(4..5)}월 " +
                "${data.transactionDate.substring(6..7)}일 " +
                "${data.transactionTime.substring(0..1)}:" +
                "${data.transactionDate.substring(2..3)}"
            if (data.deposit == "0") {
                // -
                itemLoanHistoryAmountTextView.apply {
                    text = data.withdraw
                    setTextColor(resources.getColor(R.color.colorRed))
                }
            } else {
                // +
                itemLoanHistoryAmountTextView.apply {
                    text = data.deposit
                    setTextColor(resources.getColor(R.color.colorPrimary))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemLoanHistoryBinding.inflate(
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
