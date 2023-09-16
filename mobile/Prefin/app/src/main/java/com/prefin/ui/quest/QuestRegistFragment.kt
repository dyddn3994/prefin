package com.prefin.ui.quest

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.prefin.MainActivity
import com.prefin.MainActivityViewModel
import com.prefin.R
import com.prefin.config.BaseFragment
import com.prefin.databinding.FragmentQuestRegistBinding
import com.prefin.util.StringFormatUtil
import java.util.Calendar

private const val TAG = "QuestRegistFragment prefin"
class QuestRegistFragment : BaseFragment<FragmentQuestRegistBinding>(FragmentQuestRegistBinding::bind, R.layout.fragment_quest_regist) {
    private val questRegistViewModel by viewModels<QuestRegistViewModel>()
    private val mainActivityViewModel : MainActivityViewModel by activityViewModels()
    private var selectedDate = 0L
    private lateinit var mActivity : MainActivity
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = requireActivity() as MainActivity
        init()
    }

    private fun init() = with(binding) {

        Log.d(TAG, "init: ${mainActivityViewModel.selectedQuest}")
        fragmentQuestRegistQuestNameTextView.text = mainActivityViewModel.selectedQuest!!.title
        selectedDate = System.currentTimeMillis()

        fragmentQuestRegistCalendarView.setOnDateChangeListener {view, year, month, dayOfMonth ->
            val calendar =  Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            val dateInMillis = calendar.timeInMillis
            selectedDate = dateInMillis
        }

        val today = Calendar.getInstance()
        today[Calendar.HOUR_OF_DAY] = 0
        today[Calendar.MINUTE] = 0
        today[Calendar.SECOND] = 0
        today[Calendar.MILLISECOND] = 0

        fragmentQuestRegistCalendarView.setMinDate(today.getTimeInMillis());

        fragmentQuestRegistAmountTextView.text = StringFormatUtil.moneyToWon(mainActivityViewModel.selectedQuest!!.reward)
        // 뒤로가기 버튼 클릭
        fragmentQuestRegistBackButton.setOnClickListener {
            findNavController().navigateUp()
        }

        // 등록하기 버튼 클릭
        fragmentQuestRegistRegistButton.setOnClickListener {
            questRegistViewModel.registerQuest(mainActivityViewModel.selectedChild.id, selectedDate, mainActivityViewModel.selectedQuest!!.id, System.currentTimeMillis())
            mActivity.showLoadingDialog(requireContext())
        }

        // 퀘스트 등록 observe
        questRegistViewModel.isQuestRegistSuccess.observe(viewLifecycleOwner) {
            mActivity.dismissLoadingDialog()
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
