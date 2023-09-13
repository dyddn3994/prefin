package com.prefin.ui.accountConnecting

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.prefin.MainActivityViewModel
import com.prefin.R
import com.prefin.config.BaseFragment
import com.prefin.databinding.FragmentAccountInputChildBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AccountInputChildFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AccountInputChildFragment : BaseFragment<FragmentAccountInputChildBinding>(FragmentAccountInputChildBinding::bind, R.layout.fragment_account_input_child) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private  var countDownTimer: CountDownTimer? = null
    private val accountInputChildFragmentViewModel : AccountInputChildFragmentViewModel by viewModels()
    private val mainActivityViewModel : MainActivityViewModel by activityViewModels()
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

        accountInputChildFragmentViewModel.accountChildSuccess.observe(viewLifecycleOwner){
            if(it){
                findNavController().navigate(R.id.action_AccountInputChildFragment_to_ChildAddFragment)
            }
        }

        binding.apply {

            fragmentAccountInputChildCheckButton.setOnClickListener {
                if (fragmentAccountInputChildEditText.text.isNullOrEmpty()) {
                    // fcm 전송 && 타이머 설정
                    fragmentAccountInputChildTimerLayout.visibility = View.VISIBLE
                    startTimer()
                }

            }
            fragmentAccountInputChildSettingButton.setOnClickListener {
                if(fragmentAccountInputChildCheckEditText.text.isNullOrEmpty() ){
                    Toast.makeText(requireContext(), "인증 번호를 입력해주세요", Toast.LENGTH_SHORT).show()

                }
                else if(fragmentAccountInputChildTimerTextView.text == "00:00"){
                    Toast.makeText(requireContext(), "인증 시간을 초과하였습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show()
                }
                else {
                    // 맞는지 확인하기 -> 맞다면 다음 화면
                    // 틀리면 dialog
                    if(fragmentAccountInputChildCheckEditText.text.toString() == checkText){
                        mainActivityViewModel.childUser!!.account = fragmentAccountInputChildEditText.text.toString()
                        accountInputChildFragmentViewModel.childAccountRegister(mainActivityViewModel.childUser!!.id, mainActivityViewModel.childUser!!)
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


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AccountInputChildFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AccountInputChildFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}