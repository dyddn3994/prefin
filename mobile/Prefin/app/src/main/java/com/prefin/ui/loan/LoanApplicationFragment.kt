package com.prefin.ui.loan

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

    private fun init() = with(binding) {
        fragmentLoanApplicationBeforePinmoneyTextView.text = StringFormatUtil.moneyToWon(
            mainActivityViewModel.selectedChild.allowanceAmount -
                mainActivityViewModel.selectedChild.loanAmount,
        )
        fragmentLoanApplicationInterestRateTextView.text = "${mainActivityViewModel.selectedChild.savingRate}%"

        fragmentLoanApplicationApplyButton.setOnClickListener {
            openDialog()
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
                    .multiply((mainActivityViewModel.selectedChild.loanRate ?: BigDecimal("0.0")).multiply(BigDecimal("0.01")))
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
