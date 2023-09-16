package com.prefin.ui.quest

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.prefin.MainActivity
import com.prefin.R
import com.prefin.config.ApplicationClass
import com.prefin.config.BaseFragment
import com.prefin.databinding.DialogChildQuestFinishBinding
import com.prefin.databinding.FragmentQuestChildHomeBinding
import com.prefin.databinding.ItemChildQuestBinding
import com.prefin.model.dto.QuestOwnedQuest
import com.prefin.util.StringFormatUtil

class QuestChildHomeFragment : BaseFragment<FragmentQuestChildHomeBinding>(FragmentQuestChildHomeBinding::bind, R.layout.fragment_quest_child_home) {
    private lateinit var questChildAdapter: QuestChildAdapter
    private lateinit var mActivity : MainActivity
    private val questChildHomeViewModel by viewModels<QuestChildHomeViewModel>()

    // 퀘스트 완료 신청 dialog
    private val dialog: Dialog by lazy {
        BottomSheetDialog(requireContext()).apply {
            setContentView(R.layout.dialog_child_quest_finish)
        }
    }
    private val dialogBinding: DialogChildQuestFinishBinding by lazy {
        DialogChildQuestFinishBinding.bind(dialog.findViewById(R.id.dialog_child_quest_finish_root_constraint_layout))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity =  requireActivity() as MainActivity
        init()
    }

    private fun init() = with(binding) {
        questChildAdapter = QuestChildAdapter(requireContext()).apply {
            itemClickListener = object : QuestChildAdapter.ItemClickListener {
                override fun onClick(
                    binding: ItemChildQuestBinding,
                    position: Int,
                    data: QuestOwnedQuest,
                ) {
                    showDialog(data)
                }
            }
        }
        fragmentQuestChildHomeQuestListRecyclerView.apply {
            adapter = questChildAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
        }

        questChildHomeViewModel.getQuestList(ApplicationClass.sharedPreferences.getLong("id"))
        questChildHomeViewModel.questList.observe(viewLifecycleOwner) {
            mActivity.dismissLoadingDialog()
            questChildAdapter.submitList(it)
        }

        fragmentQuestChildHomeBackButton.setOnClickListener {
            findNavController().navigateUp()
        }

        questChildHomeViewModel.questFinishRequestSuccess.observe(viewLifecycleOwner) {
            mActivity.dismissLoadingDialog()
            if (!it) {
                showSnackbar("완료 요청에 실패하였습니다.")
            } else {
                showSnackbar("퀘스트 완료 요청에 성공하였습니다.")
                questChildHomeViewModel.getQuestList(ApplicationClass.sharedPreferences.getLong("id"))
                dialog.dismiss()
            }
        }
        mActivity.showLoadingDialog(requireContext())
    }

    private fun showDialog(data: QuestOwnedQuest) = with(dialogBinding) {
        dialogChildQuestFinishQuestNameTextView.text = data.title
        dialogChildQuestFinishMoneyTextView.text = StringFormatUtil.moneyToWon(data.reward)

        dialogChildQuestFinishFinishButton.setOnClickListener {
            questChildHomeViewModel.questFinishRequest(data.questOwnedId)
        }
        dialogLoanApplicationCancelButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}
