package com.prefin.ui.loan

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.prefin.MainActivity
import com.prefin.MainActivityViewModel
import com.prefin.R
import com.prefin.config.ApplicationClass
import com.prefin.config.BaseFragment
import com.prefin.databinding.FragmentLoanHomeBinding
import com.prefin.databinding.ItemLoanHistoryBinding
import com.prefin.model.dto.LoanHistory
import com.prefin.util.StringFormatUtil

class LoanHomeFragment : BaseFragment<FragmentLoanHomeBinding>(FragmentLoanHomeBinding::bind, R.layout.fragment_loan_home) {
    private val mainActivityViewModel by activityViewModels<MainActivityViewModel>()
    private val loanHomeViewModel by viewModels<LoanHomeViewModel>()

    private lateinit var loanHistoryAdapter: LoanHistoryAdapter
    private lateinit var mActivity : MainActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = requireActivity() as MainActivity
        init()
    }

    private fun init() = with(binding) {
        fragmentLoanHomeBackButton.setOnClickListener {
            findNavController().navigateUp()
        }

        if (ApplicationClass.sharedPreferences.getString("type") == "parent") {
            fragmentLoanHomeLoanButton.visibility = View.GONE
        } else {
            fragmentLoanHomeLoanButton.visibility = View.VISIBLE
            fragmentLoanHomeLoanButton.setOnClickListener {
                findNavController().navigate(R.id.action_LoanHomeFragment_to_LoanApplicationFragment)
            }
        }

        // 대출받기 화면 이동 버튼
        fragmentLoanHomeSavingMoneyTextView.text = StringFormatUtil.moneyToWon(mainActivityViewModel.selectedChild.loanAmount)

        loanHistoryAdapter = LoanHistoryAdapter(requireContext()).apply {
            itemClickListener = object : LoanHistoryAdapter.ItemClickListener {
                override fun onClick(
                    binding: ItemLoanHistoryBinding,
                    position: Int,
                    data: LoanHistory,
                ) {
                    // 부모가 대출 요청 클릭 시 이벤트
                    if (!data.isAccepted && ApplicationClass.sharedPreferences.getString("type") == "parent") {
                        mainActivityViewModel.selectedLoanHistory = data
                        findNavController().navigate(R.id.action_LoanHomeFragment_to_LoanResponseFragment)
                    }
                }
            }
        }
        fragmentLoanHomeSavingHistoryRecyclerView.apply {
            adapter = loanHistoryAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        loanHomeViewModel.setLoanHistory(mainActivityViewModel.selectedChild.id)
        mActivity.showLoadingDialog(requireContext())
        loanHomeViewModel.loanHistories.observe(viewLifecycleOwner) {
            loanHistoryAdapter.submitList(it.reversed())
            mActivity.dismissLoadingDialog()
        }
    }
}
