package com.prefin.ui.saving

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.prefin.R
import com.prefin.config.BaseFragment
import com.prefin.databinding.FragmentWithdrawBinding

class WithdrawFragment : BaseFragment<FragmentWithdrawBinding>(FragmentWithdrawBinding::bind, R.layout.fragment_withdraw) {
    private val withdrawViewModel by viewModels<WithdrawViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() = with(binding) {
        // TODO: 총 저축 금액 default 값 입력

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
//                withdrawViewModel.withdraw()
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
