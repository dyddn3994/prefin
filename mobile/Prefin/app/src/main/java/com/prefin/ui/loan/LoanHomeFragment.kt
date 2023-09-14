package com.prefin.ui.loan

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.prefin.MainActivityViewModel
import com.prefin.R
import com.prefin.config.ApplicationClass
import com.prefin.config.BaseFragment
import com.prefin.databinding.FragmentLoanHomeBinding
import com.prefin.util.StringFormatUtil

class LoanHomeFragment : BaseFragment<FragmentLoanHomeBinding>(FragmentLoanHomeBinding::bind, R.layout.fragment_loan_home) {
    private val mainActivityViewModel by activityViewModels<MainActivityViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
    }
}
