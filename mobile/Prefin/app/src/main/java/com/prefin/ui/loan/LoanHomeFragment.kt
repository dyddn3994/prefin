package com.prefin.ui.loan

import android.os.Bundle
import android.view.View
import com.prefin.R
import com.prefin.config.BaseFragment
import com.prefin.databinding.FragmentLoanHomeBinding

class LoanHomeFragment : BaseFragment<FragmentLoanHomeBinding>(FragmentLoanHomeBinding::bind, R.layout.fragment_loan_home) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() = with(binding) {
    }
}
