package com.prefin.ui.loan

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.prefin.databinding.ItemLoanHistoryBinding
import com.prefin.model.dto.LoanHistory
import com.prefin.util.AdapterUtil
import com.prefin.util.StringFormatUtil

class LoanHistoryAdapter(var context: Context) : ListAdapter<LoanHistory, LoanHistoryAdapter.ItemViewHolder>(
    AdapterUtil.diffUtilLoanHistory,
) {
    inner class ItemViewHolder(var binding: ItemLoanHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: LoanHistory) = with(binding) {
            if (data.isAccepted) {
                itemLoanHistoryNameTextView.text = "대출"
            } else {
                itemLoanHistoryNameTextView.text = "대출 요청"
            }
            itemLoanHistoryAmountTextView.text = StringFormatUtil.moneyToWon(data.loanAmount)
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

    lateinit var itemClickListener: ItemClickListener
    interface ItemClickListener {
        fun onClick(binding: ItemLoanHistoryBinding, position: Int, data: LoanHistory)
    }
}
