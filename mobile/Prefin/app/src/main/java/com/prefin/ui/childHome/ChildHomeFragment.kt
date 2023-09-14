package com.prefin.ui.childHome

import android.os.Bundle
import android.view.View
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.prefin.MainActivityViewModel
import com.prefin.R
import com.prefin.config.BaseFragment
import com.prefin.databinding.FragmentChildHomeBinding

class ChildHomeFragment : BaseFragment<FragmentChildHomeBinding>(FragmentChildHomeBinding::bind, R.layout.fragment_child_home) {
    private val childHomeFragmentViewModel: ChildHomeFragmentViewModel by viewModels()
    private val mainActivityViewModel by activityViewModels<MainActivityViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init() {
        childHomeFragmentViewModel.getQuiz()
        
        childHomeFragmentViewModel.child.observe(viewLifecycleOwner){
            with(binding){
                mainActivityViewModel.selectedChild = it
                fragmentChildHomeMyAccountMoneyTextView.text = "${it.balance} 원"
                fragmentChildHomeSavingAccountMoneyTextView.text = "${it.savingAmount} 원"
            }
        }

        with(binding) {
            // 계좌 내역 조회
            fragmentChildHomeMyAccountLinearLayout.setOnClickListener {
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

            // 용돈 빌리기 버튼
            fragmentChildHomeLoanTextView.setOnClickListener {
                findNavController().navigate(R.id.action_ChildHomeFragment_to_LoanHomeFragment)
            }
        }
        childHomeFragmentViewModel.getChild()
    }
}
