package com.prefin.ui.quiz

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.prefin.MainActivityViewModel
import com.prefin.R
import com.prefin.config.BaseFragment
import com.prefin.databinding.FragmentQuizBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [QuizFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class QuizFragment : BaseFragment<FragmentQuizBinding>(FragmentQuizBinding::bind, R.layout.fragment_quiz), QuizDialog.OnDismissListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val quizFragmentViewModel : QuizFragmentViewModel by viewModels()
    private val mainActivityViewModel : MainActivityViewModel by activityViewModels()

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
        quizFragmentViewModel.quiz.observe(viewLifecycleOwner){
            mainActivityViewModel.quiz = it
            binding.fragmentQuizQuestionTextView.text = it.question
        }

        quizFragmentViewModel.isCorrected.observe(viewLifecycleOwner){
            if(it){
                val customDialog = QuizDialog(requireContext(), it, this)
                customDialog.show()
            }
            else{
                val customDialog = QuizDialog(requireContext(), it, this)
                customDialog.show()
            }
        }

        with(binding){

            fragmentQuizBack.setOnClickListener {

            }

            fragmentQuizAnswerButton.setOnClickListener {
                var quiz = mainActivityViewModel.quiz
                quiz!!.answer = 0
                quizFragmentViewModel.postAnswer(quiz)

            }

            fragmentQuizWrongButton.setOnClickListener {
                var quiz = mainActivityViewModel.quiz
                quiz!!.answer = 1
                quizFragmentViewModel.postAnswer(quiz)

            }


        }
        quizFragmentViewModel.getQuiz()


    }
    override fun onDismiss(isCorrected: Boolean) {

            // 화면 전환을 실행할 Fragment로 전환
            findNavController().navigate(R.id.action_QuizFragment_to_QuizAnswerFragment)

    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment QuizFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            QuizFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}