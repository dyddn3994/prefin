package com.prefin.ui.childHome

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.cardview.widget.CardView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.prefin.MainActivityViewModel
import com.prefin.R
import com.prefin.config.ApplicationClass
import com.prefin.config.BaseFragment
import com.prefin.databinding.FragmentChildHomeBinding
import com.prefin.util.StringFormatUtil

class ChildHomeFragment : BaseFragment<FragmentChildHomeBinding>(FragmentChildHomeBinding::bind, R.layout.fragment_child_home) {
    private val childHomeFragmentViewModel: ChildHomeFragmentViewModel by viewModels()
    private val mainActivityViewModel by activityViewModels<MainActivityViewModel>()
    private lateinit var cardView1: CardView
    private lateinit var cardView2: CardView
    private var isFirstCardVisible = true
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init() {

        cardView1 = binding.fragmentChildHomeCardView
        cardView2 = binding.fragmentChildHomeCardViewBack
        cardView2.visibility = View.GONE
        cardView1.setOnClickListener {
            flipCards()
        }
        cardView2.setOnClickListener{
            flipCards()
        }

        // 뒤로가기
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // 뒤로가기 동작 수행
                    isEnabled = false
                    requireActivity().onBackPressed()
                    // 앱 종료
                    requireActivity().finish()
                }
            },
        )

        childHomeFragmentViewModel.getQuiz()

        childHomeFragmentViewModel.child.observe(viewLifecycleOwner) {
            ApplicationClass.sharedPreferences.addChildUser(it)
            with(binding) {
                mainActivityViewModel.selectedChild = it
                fragmentChildHomeMyAccountMoneyTextView.text = StringFormatUtil.moneyToWon(it.balance)
                fragmentChildHomeSavingAccountMoneyTextView.text = StringFormatUtil.moneyToWon(it.savingAmount)
                fragmentChildHomeCardViewBackTrustScore.text = "현재 신뢰점수는 \n ${it.trustScore} 점 입니다."
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
                if (childHomeFragmentViewModel.child.value!!.isQuizSolved == true) {
                    Log.d("Quiz", "init: ${childHomeFragmentViewModel.quiz}")
                    mainActivityViewModel.quiz = childHomeFragmentViewModel.quiz
                    findNavController().navigate(R.id.action_ChildHomeFragment_to_QuizAnswerFragment)
                } else {
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

            // 자녀 계좌 클릭
            fragmentChildHomeMyAccountLinearLayout.setOnClickListener {
                findNavController().navigate(R.id.action_ChildHomeFragment_to_AccountHistoryFragment)
            }
        }
        childHomeFragmentViewModel.getChild()
    }

    private fun flipCards() {
        val currentCard = if (isFirstCardVisible) cardView1 else cardView2
        val nextCard = if (isFirstCardVisible) cardView2 else cardView1

        val objectAnimatorFirst =
            ObjectAnimator.ofFloat(currentCard, View.ROTATION_Y, 0f, -90f)
        objectAnimatorFirst.duration = 500
        objectAnimatorFirst.start()

        objectAnimatorFirst.addListener(object : AnimatorListenerAdapter() {

            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)

                // Hide the current card after the first animation
                currentCard.visibility = View.INVISIBLE
                // Show the next card before the second animation
                nextCard.visibility = View.VISIBLE

                val objectAnimatorSecond =
                    ObjectAnimator.ofFloat(nextCard, View.ROTATION_Y, 90f, 0f)
                objectAnimatorSecond.duration = 500
                objectAnimatorSecond.start()

                isFirstCardVisible = !isFirstCardVisible
            }
        })
    }
}
