package com.prefin.ui.account

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.prefin.MainActivityViewModel
import com.prefin.R
import com.prefin.config.BaseFragment
import com.prefin.databinding.FragmentAccountHistoryBinding

class AccountHistoryFragment : BaseFragment<FragmentAccountHistoryBinding>(FragmentAccountHistoryBinding::bind, R.layout.fragment_account_history) {
    private val accountHistoryViewModel by viewModels<AccountHistoryViewModel>()
    private val mainActivityViewModel by activityViewModels<MainActivityViewModel>()

    private lateinit var accountHistoryAdapter: AccountHistoryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() = with(binding) {
        // 뒤로가기 버튼
        fragmentAccountHistoryBackButton.setOnClickListener {
            findNavController().navigateUp()
        }

        accountHistoryViewModel.setAccountHistory(mainActivityViewModel.selectedChild.id)
        accountHistoryViewModel.accountHistory.observe(viewLifecycleOwner) {
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
    }
}
