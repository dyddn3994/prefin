package com.prefin.ui.account

import android.os.Bundle
import android.system.Os.bind
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.prefin.MainActivity
import com.prefin.MainActivityViewModel
import com.prefin.R
import com.prefin.config.ApplicationClass
import com.prefin.config.BaseFragment
import com.prefin.databinding.FragmentPinMoneySendBinding

class PinMoneySendFragment : BaseFragment<FragmentPinMoneySendBinding>(FragmentPinMoneySendBinding::bind, R.layout.fragment_pin_money_send) {
    private val pinMoneySendViewModel by viewModels<PinMoneySendViewModel>()
    private val mainActivityViewModel by activityViewModels<MainActivityViewModel>()
    private lateinit var mActivity : MainActivity
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = requireActivity() as MainActivity
        init()
    }

    private fun init() = with(binding) {
        // 뒤로가기 버튼 클릭
        fragmentPinMoneySendBackButton.setOnClickListener {
            findNavController().navigateUp()
        }

        // 전송 버튼 클릭
        fragmentPinMoneySendButton.setOnClickListener {
            if (isInvalidInput()) {
                // 입력 오류
                showSnackbar("입력값을 확인해주세요.")
            } else {
                // 전송 요청
                pinMoneySendViewModel.pinMoneySend(
                    ApplicationClass.sharedPreferences.getLong("id"),
                    mainActivityViewModel.selectedChild.id,
                    fragmentPinMoneyMoneyEditText.text.toString().toInt(),
                )
                mActivity.showLoadingDialog(requireContext())
            }
        }

        // 용돈 전송 observe
        pinMoneySendViewModel.isPinMoneySendSuccess.observe(viewLifecycleOwner) {
            mActivity.dismissLoadingDialog()
            if (!it) {
                // 용돈 전송 실패
                showSnackbar("전송에 실패하였습니다.")
            } else {
                // 용돈 전송 성공
                showSnackbar("전송에 성공하였습니다.")
                mainActivityViewModel.selectedChild.balance += fragmentPinMoneyMoneyEditText.text.toString().toInt()
                findNavController().navigateUp()
                findNavController().navigateUp()
            }
        }
    }

    private fun isInvalidInput(): Boolean = with(binding) {
        return (
            fragmentPinMoneyMoneyEditText.text.isNullOrBlank() ||
                fragmentPinMoneyMoneyEditText.text.toString().toInt() <= 0
            )
    }
}
