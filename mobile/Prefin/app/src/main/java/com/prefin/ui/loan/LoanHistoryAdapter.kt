package com.prefin.ui.loan

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.prefin.R
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
                itemLoanHistoryDateTextView.apply {
                    text = StringFormatUtil.dateTimeToString(data.loanDate)
                    visibility = View.VISIBLE
                }
            } else {
                itemLoanHistoryNameTextView.apply {
                    text = "대출 요청"
                    setTextColor(context.resources.getColor(R.color.green))
                }
                itemLoanHistoryDateTextView.visibility = View.GONE
            }
            itemLoanHistoryAmountTextView.text = StringFormatUtil.moneyToWon(data.loanAmount)

            // 부모가 대출 요청 클릭 시 이벤트
            root.setOnClickListener {
                itemClickListener.onClick(binding, layoutPosition, data)
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

    lateinit var itemClickListener: ItemClickListener
    interface ItemClickListener {
        fun onClick(binding: ItemLoanHistoryBinding, position: Int, data: LoanHistory)
    }
}
