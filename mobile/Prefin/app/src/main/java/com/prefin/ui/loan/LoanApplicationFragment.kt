package com.prefin.ui.loan

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.prefin.MainActivityViewModel
import com.prefin.R
import com.prefin.config.ApplicationClass
import com.prefin.config.BaseFragment
import com.prefin.databinding.DialogLoanApplicationBinding
import com.prefin.databinding.FragmentLoanApplicationBinding
import com.prefin.util.StringFormatUtil
import java.math.BigDecimal

class LoanApplicationFragment : BaseFragment<FragmentLoanApplicationBinding>(FragmentLoanApplicationBinding::bind, R.layout.fragment_loan_application) {
    private val loanApplicationViewModel by viewModels<LoanApplicationViewModel>()
    private val mainActivityViewModel by activityViewModels<MainActivityViewModel>()

    // 대출 신청 dialog
    private val dialog: Dialog by lazy {
        BottomSheetDialog(requireContext()).apply {
            setContentView(R.layout.dialog_loan_application)
        }
    }
    private val dialogBinding: DialogLoanApplicationBinding by lazy {
        DialogLoanApplicationBinding.bind(dialog.findViewById(R.id.dialog_loan_application_constraint_layout))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    @SuppressLint("SetTextI18n")
    private fun init() = with(binding) {
        // 뒤로가기 버튼 클릭
        fragmentLoanApplicationBackButton.setOnClickListener {
            findNavController().navigateUp()
        }

        // 다음 달 용돈은
        fragmentLoanApplicationBeforePinmoneyTextView.text = StringFormatUtil.moneyToWon(
            mainActivityViewModel.selectedChild.allowanceAmount -
                mainActivityViewModel.selectedChild.loanAmount,
        )
        // 현재 이율은
        fragmentLoanApplicationInterestRateTextView.text = "${mainActivityViewModel.selectedChild.loanRate?.multiply(BigDecimal(100))?.toFloat()}%"

        fragmentLoanApplicationApplyButton.setOnClickListener {
            if (fragmentLoanApplicationAmountEditText.text.isNullOrBlank()) {
                showSnackbar("대출받을 금액을 입력해주세요.")
            } else if (fragmentLoanApplicationAmountEditText.text.toString().toInt() < 1) {
                showSnackbar("금액은 0보다 커야합니다.")
            } else if (
                // 최대 이자 설정값 초과
                false
            ) {
                showSnackbar("대출받을 수 있는 금액이 초과하였습니다.")
            } else {
                openDialog()
            }
        }

        loanApplicationViewModel.loanApplySuccess.observe(viewLifecycleOwner) {
            if (it) {
                dialog.dismiss()
                showSnackbar("대출이 신청되었습니다. 부모님 확인 후 금액이 전송됩니다.")

                findNavController().navigateUp()
            }
        }
    }

    private fun openDialog() = with(dialogBinding) {
        dialogLoanApplicationLoanAmountTextView.text = StringFormatUtil.moneyToWon(binding.fragmentLoanApplicationAmountEditText.text.toString().toInt())
        dialogLoanApplicationBeforePinmoneyTextView.text = StringFormatUtil.moneyToWon(
            mainActivityViewModel.selectedChild.allowanceAmount -
                mainActivityViewModel.selectedChild.loanAmount,
        )
        dialogLoanApplicationAfterPinmoneyTextView.text = StringFormatUtil.moneyToWon(
            mainActivityViewModel.selectedChild.allowanceAmount -
                mainActivityViewModel.selectedChild.loanAmount -
                binding.fragmentLoanApplicationAmountEditText.text.toString().toInt() -
                binding.fragmentLoanApplicationAmountEditText.text.toString().toBigDecimal()
                    .multiply((mainActivityViewModel.selectedChild.loanRate ?: BigDecimal("0.0")))
                    .toInt(),
        )

        dialogLoanApplicationApplyButton.setOnClickListener {
            loanApplicationViewModel.askForMoney(
                binding.fragmentLoanApplicationAmountEditText.text.toString().toInt(),
                ApplicationClass.sharedPreferences.getLong("id"),
                mainActivityViewModel.selectedChild.id,
            )
        }
        dialogLoanApplicationCancelButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}
