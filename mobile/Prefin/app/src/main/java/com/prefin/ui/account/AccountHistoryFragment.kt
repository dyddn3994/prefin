package com.prefin.ui.account

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.prefin.MainActivity
import com.prefin.MainActivityViewModel
import com.prefin.R
import com.prefin.config.BaseFragment
import com.prefin.databinding.FragmentAccountHistoryBinding
import com.prefin.util.StringFormatUtil

class AccountHistoryFragment : BaseFragment<FragmentAccountHistoryBinding>(FragmentAccountHistoryBinding::bind, R.layout.fragment_account_history) {
    private val accountHistoryViewModel by viewModels<AccountHistoryViewModel>()
    private val mainActivityViewModel by activityViewModels<MainActivityViewModel>()
    private lateinit var mActivity : MainActivity
    private lateinit var accountHistoryAdapter: AccountHistoryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = requireActivity() as MainActivity
        init()
    }

    private fun init() = with(binding) {
        // 뒤로가기 버튼
        fragmentAccountHistoryBackButton.setOnClickListener {
            findNavController().navigateUp()
        }


        accountHistoryViewModel.setAccountHistory(mainActivityViewModel.selectedChild.id)
        accountHistoryViewModel.accountHistory.observe(viewLifecycleOwner) {
            mActivity.dismissLoadingDialog()
            accountHistoryAdapter.submitList(
                it.sortedByDescending {
                    it.transactionTime
                }.sortedByDescending {
                    it.transactionDate
                },
            )
        }

        accountHistoryAdapter = AccountHistoryAdapter(requireContext())
        fragmentAccountHistoryRecyclerView.apply {
            adapter = accountHistoryAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        fragmentAccountHistoryAllowanceDayTextView.text = "매월 ${mainActivityViewModel.selectedChild.payday} 일"
        fragmentAccountHistoryAllowanceAmountTextView.text = StringFormatUtil.moneyToWon(mainActivityViewModel.selectedChild.allowanceAmount)
        mActivity.showLoadingDialog(requireContext())
    }
}
