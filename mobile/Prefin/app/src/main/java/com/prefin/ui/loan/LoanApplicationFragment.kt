package com.prefin.ui.loan

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.prefin.MainActivity
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
    private lateinit var mActivity: MainActivity
    // 대출 신청 dialog
    private val dialog: Dialog by lazy {
        BottomSheetDialog(requireContext()).apply {
            setContentView(R.layout.dialog_loan_application)
        }
    }
    private val dialogBinding: DialogLoanApplicationBinding by lazy {
        DialogLoanApplicationBinding.bind(dialog.findViewById(R.id.dialog_loan_application_constraint_layout))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = requireActivity() as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        mActivity = requireActivity() as MainActivity
        init()
    }


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

        val canLoanAmount = mainActivityViewModel.selectedChild.possibleLoanAmount
        fragmentLoanApplicationCanLoanAmountTextView.text = "현재 대출 가능한 금액은 ${canLoanAmount}원 입니다."
        fragmentLoanApplicationAmountEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val inputAmount = p0.toString().toIntOrNull()
                if (inputAmount != null) {
                    if (canLoanAmount < inputAmount) {
                        fragmentLoanApplicationApplyButton.isClickable = false
                        fragmentLoanApplicationApplyButton.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.colorGray))
                    } else {
                        fragmentLoanApplicationApplyButton.isClickable = true
                        fragmentLoanApplicationApplyButton.backgroundTintList = ColorStateList.valueOf(
                            resources.getColor(R.color.colorPrimary))
                    }
                }


            }
            override fun afterTextChanged(p0: Editable?) {

            }
        })


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
            mActivity.dismissLoadingDialog()
            if (it) {
                dialog.dismiss()
                showSnackbar("대출이 신청되었습니다. 부모님 확인 후 금액이 전송됩니다.")
                findNavController().navigateUp()
            }
            else{
                showSnackbar("대출 신청이 실패되었습니다. 다시 시도해주십시오")
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
                mainActivityViewModel.selectedChild.parentId,
                mainActivityViewModel.selectedChild.id,
            )
            mActivity.showLoadingDialog(requireContext())
        }
        dialogLoanApplicationCancelButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}
