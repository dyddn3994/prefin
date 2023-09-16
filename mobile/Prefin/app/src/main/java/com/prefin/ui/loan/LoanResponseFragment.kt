package com.prefin.ui.loan

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.prefin.MainActivity
import com.prefin.MainActivityViewModel
import com.prefin.R
import com.prefin.config.BaseFragment
import com.prefin.databinding.FragmentLoanResponseBinding
import com.prefin.util.StringFormatUtil
import java.math.BigDecimal

class LoanResponseFragment : BaseFragment<FragmentLoanResponseBinding>(FragmentLoanResponseBinding::bind, R.layout.fragment_loan_response) {
    private val loanResponseViewModel by viewModels<LoanResponseViewModel>()
    private val mainActivityViewModel by activityViewModels<MainActivityViewModel>()
    private lateinit var mActivity : MainActivity
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = requireActivity() as MainActivity
        init()
    }

    @SuppressLint("SetTextI18n")
    private fun init() = with(binding) {
        loanResponseViewModel.loanGiveMoneySuccess.observe(viewLifecycleOwner) {
            mActivity.dismissLoadingDialog()
            if (!it) {
                showSnackbar("대출 처리에 실패하였습니다.")
            } else {
                showSnackbar("대출 처리에 성공하였습니다.")
                mainActivityViewModel.selectedChild.loanAmount += mainActivityViewModel.selectedLoanHistory.loanAmount
                findNavController().navigateUp()
            }
        }

        // 대출 승낙하기 버튼 클릭
        fragmentLoanResponseApplyButton.setOnClickListener {
            loanResponseViewModel.giveMoney(mainActivityViewModel.selectedLoanHistory.loanId)
            mActivity.showLoadingDialog(requireContext())
        }

        fragmentLoanResponseLoanAmountTextView.text = StringFormatUtil.moneyToWon(mainActivityViewModel.selectedLoanHistory.loanAmount)
        fragmentLoanResponseInterestRateTextView.text =
            mainActivityViewModel.selectedChild.loanRate!!
                .multiply(BigDecimal(100))
                .toFloat()
                .toString() + "%"
        fragmentLoanResponseAfterPinmoneyTextView.text = StringFormatUtil.moneyToWon(
            mainActivityViewModel.selectedChild.allowanceAmount -
                mainActivityViewModel.selectedChild.loanAmount -
                mainActivityViewModel.selectedLoanHistory.loanAmount -
                mainActivityViewModel.selectedLoanHistory.loanAmount.toBigDecimal()
                    .multiply((mainActivityViewModel.selectedChild.loanRate ?: BigDecimal("0.0")))
                    .toInt(),
        )
    }
}
