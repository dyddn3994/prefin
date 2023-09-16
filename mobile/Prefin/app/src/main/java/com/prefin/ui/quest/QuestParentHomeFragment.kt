package com.prefin.ui.quest

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.prefin.MainActivity
import com.prefin.MainActivityViewModel
import com.prefin.R
import com.prefin.config.BaseFragment
import com.prefin.databinding.DialogLoanApplicationBinding
import com.prefin.databinding.FragmentQuestParentHomeBinding
import com.prefin.databinding.QuestCompleteBottomSheetBinding
import com.prefin.model.dto.Quest
import com.prefin.model.dto.QuestOwned
import com.prefin.model.dto.QuestOwnedQuest
import com.prefin.util.StringFormatUtil

class QuestParentHomeFragment : BaseFragment<FragmentQuestParentHomeBinding>(FragmentQuestParentHomeBinding::bind, R.layout.fragment_quest_parent_home) {
    private lateinit var questParentAdapter: QuestParentAdapter
    private val questParentHomeFragmentViewModel : QuestParentHomeFragmentViewModel by viewModels()
    private val mainActivityViewModel : MainActivityViewModel by activityViewModels()
    private lateinit var mActivity : MainActivity

    private val dialog: Dialog by lazy {
        BottomSheetDialog(requireContext()).apply {
            setContentView(R.layout.quest_complete_bottom_sheet)
        }
    }
    private val dialogBinding: QuestCompleteBottomSheetBinding by lazy {
        QuestCompleteBottomSheetBinding.bind(dialog.findViewById(R.id.quest_bottom_sheet))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = requireActivity() as MainActivity
        questParentHomeFragmentViewModel.getQuestList(mainActivityViewModel.selectedChild.id)
        questParentHomeFragmentViewModel.questList.observe(viewLifecycleOwner){
            questParentAdapter.submitList(it)
            mActivity.dismissLoadingDialog()
        }

        init()
        mActivity.showLoadingDialog(requireContext())
    }

    private fun init() = with(binding) {
        // 뒤로가기 버튼 클릭
        fragmentQuestParentHomeBackButton.setOnClickListener {
            findNavController().navigateUp()
        }
        
        questParentAdapter = QuestParentAdapter(requireContext())
        fragmentQuestParentHomeQuestListRecyclerView.apply {
            adapter = questParentAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }


        questParentHomeFragmentViewModel.questCompleteSuccess.observe(viewLifecycleOwner){
            mActivity.dismissLoadingDialog()
            if(it == 1){
                questParentHomeFragmentViewModel.getQuestList(mainActivityViewModel.selectedChild.id)
            }
            else if(it == 2){
                showSnackbar("퀘스트 완료 처리를 실패했습니다. (잔액 부족)")
            }
            else {
                showSnackbar("서버와의 연결이 불안정 합니다.")
            }
        }

        questParentAdapter.itemClickListener  = object : QuestParentAdapter.ItemClickListener{
            override fun onClick(view: View, position: Int, data: QuestOwnedQuest) {
                with(dialogBinding){
                    bottomSheetQuestTitle.text = data.title
                    bottomSheetQuestReward.text = StringFormatUtil.moneyToWon(data.reward)
                    bottomSheetQuestApproveButton.setOnClickListener {
                        if(data.requested){
                            questParentHomeFragmentViewModel.questComplete(data.questOwnedId)
                            dialog.dismiss()
                            mActivity.showLoadingDialog(requireContext())
                        }

                    }
                    bottomSheetQuestRefuseButton.setOnClickListener {
                        dialog.dismiss()
                    }
                }

                //bottomSeat 띄우기
                if(data.requested){
                    dialog.show()
                }

            }


        }
        
        fragmentQuestParentHomeQuestItemListImageView.setOnClickListener {
            findNavController().navigate(R.id.action_QuestParentHomeFragment_to_QuestParentItemFragment)
        }
    }
}
