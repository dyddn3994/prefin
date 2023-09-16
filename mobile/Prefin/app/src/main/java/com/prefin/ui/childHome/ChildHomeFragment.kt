package com.prefin.ui.childHome

import android.os.Bundle
import android.view.View
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.prefin.MainActivityViewModel
import com.prefin.R
import com.prefin.config.BaseFragment
import com.prefin.databinding.FragmentChildHomeBinding
import com.prefin.util.StringFormatUtil

class ChildHomeFragment : BaseFragment<FragmentChildHomeBinding>(FragmentChildHomeBinding::bind, R.layout.fragment_child_home) {
    private val childHomeFragmentViewModel: ChildHomeFragmentViewModel by viewModels()
    private val mainActivityViewModel by activityViewModels<MainActivityViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init() {

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                // 뒤로가기 동작 수행
                isEnabled = false
                requireActivity().onBackPressed()
                // 앱 종료
                requireActivity().finish()

            }
        })

        childHomeFragmentViewModel.getQuiz()
        
        childHomeFragmentViewModel.child.observe(viewLifecycleOwner){
            with(binding){
                mainActivityViewModel.selectedChild = it
                fragmentChildHomeMyAccountMoneyTextView.text = StringFormatUtil.moneyToWon( it.balance)
                fragmentChildHomeSavingAccountMoneyTextView.text =  StringFormatUtil.moneyToWon(it.savingAmount)
            }
        }

        with(binding) {
            // 계좌 내역 조회
            fragmentChildHomeMyAccountLinearLayout.setOnClickListener {
            }
            fragmentChildHomeNotiImageView.setOnClickListener {
                findNavController().navigate(R.id.action_ChildHomeFragment_to_NotiFragment)
            }

            // 저축 내역 조회
            fragmentChildHomeSavingAccountLinearLayout.setOnClickListener {
                findNavController().navigate(R.id.action_ChildHomeFragment_to_SavingHomeFragment)
            }

            fragmentChildHomeSettingImageView.setOnClickListener {
                findNavController().navigate(R.id.action_ChildHomeFragment_to_SettingFragment)
            }

            fragmentChildHomeQuizImageView.setOnClickListener {
                if(childHomeFragmentViewModel.child.value!!.isQuizSolved == true){

                    Log.d("Quiz", "init: ${childHomeFragmentViewModel.quiz}")
                    mainActivityViewModel.quiz = childHomeFragmentViewModel.quiz
                    findNavController().navigate(R.id.action_ChildHomeFragment_to_QuizAnswerFragment)
                }
                else{
                    findNavController().navigate(R.id.action_ChildHomeFragment_to_QuizFragment)
                }
            }

            // 퀘스트 버튼
            fragmentChildHomeQuestTextView.setOnClickListener {
                findNavController().navigate(R.id.action_ChildHomeFragment_to_QuestChildHomeFragment)
            }
            
            // 용돈 빌리기 버튼
            fragmentChildHomeLoanTextView.setOnClickListener {
                findNavController().navigate(R.id.action_ChildHomeFragment_to_LoanHomeFragment)
            }
        }
        childHomeFragmentViewModel.getChild()
    }
}
