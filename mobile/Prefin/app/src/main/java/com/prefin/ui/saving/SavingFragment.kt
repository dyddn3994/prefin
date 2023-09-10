package com.prefin.ui.saving

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.prefin.R
import com.prefin.config.BaseFragment
import com.prefin.databinding.FragmentSavingBinding

class SavingFragment : BaseFragment<FragmentSavingBinding>(FragmentSavingBinding::bind, R.layout.fragment_saving) {
    private val savingViewModel by viewModels<SavingViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() = with(binding) {
        // TODO: 총 저축 금액 default 값 입력

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
//                savingViewModel.saving()
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
