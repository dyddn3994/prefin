package com.prefin.ui.account

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.prefin.R
import com.prefin.config.BaseFragment
import com.prefin.databinding.FragmentPinMoneyRegistBinding

class PinMoneyRegistFragment : BaseFragment<FragmentPinMoneyRegistBinding>(FragmentPinMoneyRegistBinding::bind, R.layout.fragment_pin_money_regist) {
    private val pinMoneyRegistViewModel by viewModels<PinMoneyRegistViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() = with(binding) {
        // TODO: 수정 시 default값 입력

        // 뒤로가기 버튼 클릭
        fragmentPinMoneyRegistBackButton.setOnClickListener {
            findNavController().navigateUp()
        }

        // 등록 버튼 클릭
        fragmentPinMoneyRegistRegistButton.setOnClickListener {
            if (isInvalidInput()) {
                // 입력 오류
                showSnackbar("입력값을 확인해주세요.")
            } else {
                // 등록 요청
//                accountViewModel.pinMoneySet()
            }
        }

        // 정기 용돈 등록 observe
        pinMoneyRegistViewModel.isPinMoneySetSuccess.observe(viewLifecycleOwner) {
            if (!it) {
                // 정기용돈 등록 실패
                showSnackbar("등록에 실패하였습니다.")
            } else {
                // 정기용돈 등록 성공
                showSnackbar("등록에 성공하였습니다.")
                findNavController().navigateUp()
            }
        }
    }

    private fun isInvalidInput(): Boolean = with(binding) {
        return (
            fragmentPinMoneyRegistDateEditText.text.isNullOrBlank() ||
                fragmentPinMoneyRegistDateEditText.text.toString().toInt() !in 1..31 ||
                fragmentPinMoneyRegistAmountEditText.text.isNullOrBlank() ||
                fragmentPinMoneyRegistAmountEditText.text.toString().toInt() <= 0
            )
    }
}
