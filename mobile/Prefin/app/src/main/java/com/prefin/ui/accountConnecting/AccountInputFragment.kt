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
import com.prefin.databinding.FragmentAccountInputBinding

class AccountInputFragment : BaseFragment<FragmentAccountInputBinding>(FragmentAccountInputBinding::bind, R.layout.fragment_account_input) {

    private val accountInputFragmentViewModel: AccountInputFragmentViewModel by viewModels()
    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()
    private var countDownTimer: CountDownTimer? = null
    private val checkText = "신한브로"
    private lateinit var mActivity: MainActivity
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = requireActivity() as MainActivity
        init()
    }

    fun init() {
        binding.apply {
            accountInputFragmentViewModel.accountRegisterSuccess.observe(viewLifecycleOwner) {
                mActivity.dismissLoadingDialog()
                if (it) {
                    mainActivityViewModel.fromFragment = AccountInputFragment::class.simpleName
                    findNavController().navigate(R.id.action_AccountInputFragment_to_SimplePassFragment)
                }
            }

            fragmentAccountInputCheckButton.setOnClickListener {
                if (!fragmentAccountInputEditText.text.isNullOrEmpty()) {
                    // 타이머 설정
                    fragmentAccountInputTimerLinearLayout.visibility = View.VISIBLE
                    startTimer()

                    fragmentAccountInputCheckEditText.requestFocus()
                }
            }
            fragmentAccountInputSettingButton.setOnClickListener {
                if (fragmentAccountInputCheckEditText.text.isNullOrEmpty()) {
                    showSnackbar("인증 번호를 입력해주세요")
                } else if (fragmentAccountInputTimerTextView.text == "00:00") {
                    showSnackbar("인증 시간을 초과하였습니다. 다시 시도해주세요")
                } else {
                    // 맞는지 확인하기 -> 맞다면 다음 화면
                    // 틀리면 dialog

                    if (fragmentAccountInputCheckEditText.text.toString() == checkText) {
                        mainActivityViewModel.parentUser!!.account = fragmentAccountInputEditText.text.toString()
                        accountInputFragmentViewModel.accountRegister(mainActivityViewModel.parentUser!!.id, mainActivityViewModel.parentUser!!)
                        mActivity.showLoadingDialog(requireContext())
                    } else {
                        showSnackbar("입력값을 확인해주세요.")
                    }
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
                binding.fragmentAccountInputTimerTextView.text = String.format("%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                binding.fragmentAccountInputTimerTextView.text = "00:00"
            }
        }
        countDownTimer?.start()
    }

    private fun stopTimer() {
        countDownTimer?.cancel()
        binding.fragmentAccountInputTimerTextView.text = "3:00"
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
