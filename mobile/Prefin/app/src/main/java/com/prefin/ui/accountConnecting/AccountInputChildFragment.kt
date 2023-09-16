package com.prefin.ui.accountConnecting

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.prefin.MainActivity
import com.prefin.MainActivityViewModel
import com.prefin.R
import com.prefin.config.BaseFragment
import com.prefin.databinding.FragmentAccountInputChildBinding

class AccountInputChildFragment : BaseFragment<FragmentAccountInputChildBinding>(FragmentAccountInputChildBinding::bind, R.layout.fragment_account_input_child) {
    private var countDownTimer: CountDownTimer? = null
    private val accountInputChildFragmentViewModel: AccountInputChildFragmentViewModel by viewModels()
    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()
    private val checkText = "신한브로"
    private lateinit var mActivity : MainActivity
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = requireActivity() as MainActivity
        init()
    }

    fun init() {
        accountInputChildFragmentViewModel.accountChildSuccess.observe(viewLifecycleOwner) {
            mActivity.dismissLoadingDialog()
            if (it) {
                findNavController().navigate(R.id.action_AccountInputChildFragment_to_ChildAddFragment)
            }
        }

        binding.apply {
            fragmentAccountInputChildCheckButton.setOnClickListener {
                if (!fragmentAccountInputChildEditText.text.isNullOrEmpty()) {
                    // fcm 전송 && 타이머 설정
                    fragmentAccountInputChildTimerLayout.visibility = View.VISIBLE
                    startTimer()
                    fragmentAccountInputChildCheckEditText.requestFocus()
                }
            }
            fragmentAccountInputChildSettingButton.setOnClickListener {
                if (fragmentAccountInputChildCheckEditText.text.isNullOrEmpty()) {
                    showSnackbar("인증 번호를 입력해주세요")
                } else if (fragmentAccountInputChildTimerTextView.text == "00:00") {
                    showSnackbar("인증 시간을 초과하였습니다. 다시 시도해주세요")
                } else {
                    // 맞는지 확인하기 -> 맞다면 다음 화면
                    // 틀리면 dialog
//                    if (fragmentAccountInputChildCheckEditText.text.toString() == checkText) {
                    mainActivityViewModel.childUser!!.account = fragmentAccountInputChildEditText.text.toString()
                    accountInputChildFragmentViewModel.childAccountRegister(mainActivityViewModel.childUser!!.id, mainActivityViewModel.childUser!!)
                    mActivity.showLoadingDialog(requireContext())
//                    }
                }
            }
        }
    }

    private fun startTimer() {
        countDownTimer?.cancel()

        countDownTimer = object : CountDownTimer(180000, 1000) { // 3분을 밀리초로 표현
            override fun onTick(millisUntilFinished: Long) {
                val minutes = millisUntilFinished / 60000
                val seconds = (millisUntilFinished % 60000) / 1000
                binding.fragmentAccountInputChildTimerTextView.text = String.format("%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                binding.fragmentAccountInputChildTimerTextView.text = "00:00"
            }
        }
        countDownTimer?.start()
    }

    private fun stopTimer() {
        countDownTimer?.cancel()
        binding.fragmentAccountInputChildTimerTextView.text = "3:00"
    }

    override fun onStop() {
        super.onStop()
        stopTimer()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopTimer()
    }
}
