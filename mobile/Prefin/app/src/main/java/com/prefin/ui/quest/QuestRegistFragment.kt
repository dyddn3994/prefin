package com.prefin.ui.quest

import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.DatePicker.OnDateChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.prefin.MainActivityViewModel
import com.prefin.R
import com.prefin.config.BaseFragment
import com.prefin.databinding.FragmentQuestRegistBinding
import com.prefin.util.StringFormatUtil

private const val TAG = "QuestRegistFragment prefin"
class QuestRegistFragment : BaseFragment<FragmentQuestRegistBinding>(FragmentQuestRegistBinding::bind, R.layout.fragment_quest_regist) {
    private val questRegistViewModel by viewModels<QuestRegistViewModel>()
    private val mainActivityViewModel : MainActivityViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() = with(binding) {

        Log.d(TAG, "init: ${mainActivityViewModel.selectedQuest}")
        fragmentQuestRegistQuestNameTextView.text = mainActivityViewModel.selectedQuest!!.title

        fragmentQuestRegistCalendarView.date


        fragmentQuestRegistAmountTextView.text = StringFormatUtil.moneyToWon(mainActivityViewModel.selectedQuest!!.reward)
        // 뒤로가기 버튼 클릭
        fragmentQuestRegistBackButton.setOnClickListener {
            findNavController().navigateUp()
        }

        // 등록하기 버튼 클릭
        fragmentQuestRegistRegistButton.setOnClickListener {
            questRegistViewModel.registerQuest(mainActivityViewModel.selectedChild.id, fragmentQuestRegistCalendarView.date, mainActivityViewModel.selectedQuest!!.id, System.currentTimeMillis())
        }

        // 퀘스트 등록 observe
        questRegistViewModel.isQuestRegistSuccess.observe(viewLifecycleOwner) {
            if (!it) {
                // 퀘스트 등록 실패
                showSnackbar("퀘스트 등록에 실패하였습니다.")
            } else {
                // 퀘스트 등록 성공
                showSnackbar("퀘스트가 등록되었습니다.")
                findNavController().navigateUp()
            }
        }
    }
}
