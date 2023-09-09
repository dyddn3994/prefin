package com.prefin.ui.accountConnecting

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.View
import com.prefin.R
import com.prefin.config.BaseFragment
import com.prefin.databinding.FragmentAccountInputBinding
import kotlin.concurrent.timer

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AccountInputFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AccountInputFragment : BaseFragment<FragmentAccountInputBinding>(FragmentAccountInputBinding::bind, R.layout.fragment_account_input) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var countDownTimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }


    fun init() {
        binding.apply {

            fragmentAccountInputCheckButton.setOnClickListener {
                if (fragmentAccountInputEditText.text.isNullOrEmpty()) {
                    // fcm 전송 && 타이머 설정
                    startTimer()
                }

            }
            fragmentAccountInputSettingButton.setOnClickListener {
                if(fragmentAccountInputCheckEditText.text.isNullOrEmpty() && fragmentAccountInputTimerTextView.text != "00:00"){
                    // 맞는지 확인하기 -> 맞다면 다음 화면
                    // 틀리면 dialog

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
        countDownTimer.start()
    }

    private fun stopTimer() {
        countDownTimer.cancel()
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