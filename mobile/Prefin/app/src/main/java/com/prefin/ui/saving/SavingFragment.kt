package com.prefin.ui.saving

import android.annotation.SuppressLint
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
import com.prefin.databinding.FragmentSavingBinding
import com.prefin.util.StringFormatUtil
import java.math.BigDecimal

class SavingFragment : BaseFragment<FragmentSavingBinding>(FragmentSavingBinding::bind, R.layout.fragment_saving) {
    private val savingViewModel by viewModels<SavingViewModel>()
    private val mainActivityViewModel by activityViewModels<MainActivityViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    @SuppressLint("SetTextI18n")
    private fun init() = with(binding) {
        fragmentSavingBeforeTotalAmountTextView.text = StringFormatUtil.moneyToWon(mainActivityViewModel.selectedChild.savingAmount)
        fragmentSavingBeforeInterestTextView.text = StringFormatUtil.moneyToWon(
            mainActivityViewModel.selectedChild.savingAmount
                .toBigDecimal()
                .multiply((mainActivityViewModel.selectedChild.savingRate ?: BigDecimal("0.0")))
                .toInt(),
        )

        fragmentSavingAmountEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val inputNum =
                    if (fragmentSavingAmountEditText.text.isNullOrBlank()) {
                        0
                    } else {
                        fragmentSavingAmountEditText.text.toString().toInt()
                    }

                val sum = mainActivityViewModel.selectedChild.savingAmount + inputNum
                fragmentSavingAfterTotalAmountTextView.text = StringFormatUtil.moneyToWon(sum)
                fragmentSavingAfterTotalInterestTextView.text = StringFormatUtil.moneyToWon(
                    sum.toBigDecimal()
                        .multiply((mainActivityViewModel.selectedChild.savingRate ?: BigDecimal("0.0")))
                        .toInt(),
                )
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        })

        // 뒤로가기 버튼 클릭
        fragmentSavingBackButton.setOnClickListener {
            findNavController().navigateUp()
        }

        // 저축하기 버튼 클릭
        fragmentSavingSavingButton.setOnClickListener {
            if (invalidInput()) {
                // 입력 오류
                showSnackbar("입력값을 확인해주세요.")
            } else {
                // 저축 요청
                savingViewModel.saving(
                    mainActivityViewModel.selectedChild.id,
                    fragmentSavingAmountEditText.text.toString().toInt(),
                )
            }
        }

        // 저축 observe
        savingViewModel.isSavingSuccess.observe(viewLifecycleOwner) {
            if (!it) {
                // 저축 실패
                showSnackbar("저축에 실패하였습니다.")
            } else {
                // 저축 성공
                showSnackbar("성공적으로 저축되었습니다.")

                mainActivityViewModel.selectedChild.savingAmount += fragmentSavingAmountEditText.text.toString().toInt()
                findNavController().navigateUp()
            }
        }
    }

    private fun invalidInput(): Boolean = with(binding) {
        return (
            fragmentSavingAmountEditText.text.isNullOrBlank() ||
                fragmentSavingAmountEditText.text.toString().toInt() <= 0
            )
    }
}
