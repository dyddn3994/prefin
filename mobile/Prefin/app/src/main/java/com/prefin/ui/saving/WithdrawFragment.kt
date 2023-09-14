package com.prefin.ui.saving

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.prefin.MainActivityViewModel
import com.prefin.R
import com.prefin.config.BaseFragment
import com.prefin.databinding.FragmentWithdrawBinding
import com.prefin.util.StringFormatUtil
import java.math.BigDecimal

class WithdrawFragment : BaseFragment<FragmentWithdrawBinding>(FragmentWithdrawBinding::bind, R.layout.fragment_withdraw) {
    private val withdrawViewModel by viewModels<WithdrawViewModel>()
    private val mainActivityViewModel by activityViewModels<MainActivityViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() = with(binding) {
        fragmentWithdrawBeforeTotalAmountTextView.text = StringFormatUtil.moneyToWon(mainActivityViewModel.selectedChild.savingAmount)
        fragmentWithdrawBeforeInterestTextView.text = StringFormatUtil.moneyToWon(
            mainActivityViewModel.selectedChild.savingAmount
                .toBigDecimal()
                .multiply((mainActivityViewModel.selectedChild.savingRate ?: BigDecimal("0.0")).multiply(BigDecimal("0.01")))
                .add(mainActivityViewModel.selectedChild.savingAmount.toBigDecimal())
                .toInt(),
        )

        fragmentWithdrawAmountEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val sub = mainActivityViewModel.selectedChild.savingAmount - fragmentWithdrawAmountEditText.text.toString().toInt()
                fragmentWithdrawAfterTotalAmountTextView.text = StringFormatUtil.moneyToWon(sub)
                fragmentWithdrawAfterTotalInterestTextView.text = StringFormatUtil.moneyToWon(
                    sub.toBigDecimal()
                        .multiply((mainActivityViewModel.selectedChild.savingRate ?: BigDecimal("0.0")).multiply(BigDecimal("0.01")))
                        .add(sub.toBigDecimal())
                        .toInt(),
                )
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        })

        // 뒤로가기 버튼 클릭
        fragmentWithdrawBackButton.setOnClickListener {
            findNavController().navigateUp()
        }

        // 인출하기 버튼 클릭
        fragmentWithdrawWithdrawButton.setOnClickListener {
            if (invalidInput()) {
                // 입력 오류
                showSnackbar("입력값을 확인해주세요.")
            } else {
                // 인출 요청
                withdrawViewModel.withdraw(
                    mainActivityViewModel.selectedChild.id,
                    fragmentWithdrawAmountEditText.text.toString().toInt(),
                )
            }
        }

        // 인출 observe
        withdrawViewModel.isWithdrawSuccess.observe(viewLifecycleOwner) {
            if (!it) {
                // 인출 실패
                showSnackbar("인출에 실패하였습니다.")
            } else {
                // 인출 성공
                showSnackbar("성공적으로 인출되었습니다.")

                mainActivityViewModel.selectedChild.savingAmount -= fragmentWithdrawAmountEditText.text.toString().toInt()
                findNavController().navigateUp()
            }
        }
    }

    private fun invalidInput(): Boolean = with(binding) {
        return (
            fragmentWithdrawAmountEditText.text.isNullOrBlank() ||
                fragmentWithdrawAmountEditText.text.toString().toInt() <= 0
            )
    }
}
