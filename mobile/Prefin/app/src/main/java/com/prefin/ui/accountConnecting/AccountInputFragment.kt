package com.prefin.ui.accountConnecting

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.prefin.MainActivityViewModel
import com.prefin.R
import com.prefin.config.BaseFragment
import com.prefin.databinding.FragmentAccountInputBinding

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
    private val accountInputFragmentViewModel : AccountInputFragmentViewModel by viewModels()
    private val mainActivityViewModel : MainActivityViewModel by activityViewModels()
    private  var countDownTimer: CountDownTimer? = null
    private val checkText = "신한브로"
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

            accountInputFragmentViewModel.accountRegisterSuccess.observe(viewLifecycleOwner){
                if(it){
                    findNavController().navigate(R.id.action_AccountInputFragment_to_ChildAddFragment)
                }
            }

            fragmentAccountInputCheckButton.setOnClickListener {
                if (!fragmentAccountInputEditText.text.isNullOrEmpty()) {
                  // 타이머 설정
                    fragmentAccountInputTimerLinearLayout.visibility = View.VISIBLE
                    startTimer()
                }

            }
            fragmentAccountInputSettingButton.setOnClickListener {
                if(fragmentAccountInputCheckEditText.text.isNullOrEmpty() ){
                    Toast.makeText(requireContext(), "인증 번호를 입력해주세요", Toast.LENGTH_SHORT).show()

                }
                else if(fragmentAccountInputTimerTextView.text == "00:00"){
                    Toast.makeText(requireContext(), "인증 시간을 초과하였습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show()
                }
                else {
                    // 맞는지 확인하기 -> 맞다면 다음 화면
                    // 틀리면 dialog
                    if(fragmentAccountInputCheckEditText.text.toString() == checkText) {
                        mainActivityViewModel.parentUser!!.account = fragmentAccountInputEditText.text.toString()
                       accountInputFragmentViewModel.accountRegister(mainActivityViewModel.parentUser!!.id, mainActivityViewModel.parentUser!!)
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