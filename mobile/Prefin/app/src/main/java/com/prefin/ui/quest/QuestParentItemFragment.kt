package com.prefin.ui.quest

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.prefin.MainActivity
import com.prefin.MainActivityViewModel
import com.prefin.R
import com.prefin.config.ApplicationClass
import com.prefin.config.BaseFragment
import com.prefin.databinding.DialogBottomSheetChooseQuestBinding
import com.prefin.databinding.DialogLoanApplicationBinding
import com.prefin.databinding.FragmentQuestParentItemBinding
import com.prefin.model.dto.Quest
import com.prefin.util.StringFormatUtil
import java.math.BigDecimal

class QuestParentItemFragment : BaseFragment<FragmentQuestParentItemBinding>(FragmentQuestParentItemBinding::bind, R.layout.fragment_quest_parent_item) {
    private lateinit var questParentItemAdapter: QuestParentItemAdapter

    private val questParentItemViewModel by viewModels<QuestParentItemViewModel>()
    private val mainActivityViewModel : MainActivityViewModel by activityViewModels()
    private lateinit var mActivity : MainActivity

    private val dialog: Dialog by lazy {
        BottomSheetDialog(requireContext()).apply {
            setContentView(R.layout.dialog_bottom_sheet_choose_quest)
        }
    }
    private val dialogBinding: DialogBottomSheetChooseQuestBinding by lazy {
        DialogBottomSheetChooseQuestBinding.bind(dialog.findViewById(R.id.dialog_bottom_sheet_choose_quest_constraint_layout))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = requireActivity() as MainActivity
        questParentItemViewModel.requestQuestItemList(ApplicationClass.sharedPreferences.getLong("id"))
        init()
        mActivity.showLoadingDialog(requireContext())

    }

    private fun init() = with(binding) {
        questParentItemAdapter = QuestParentItemAdapter(requireContext())
        fragmentQuestParentItemQuestListRecyclerView.apply {
            adapter = questParentItemAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        // 뒤로가기 버튼
        fragmentQuestParentItemBackButton.setOnClickListener {
            findNavController().navigateUp()
        }

        // 퀘스트 생성 버튼 클릭
        fragmentQuestParentItemQuestItemListImageView.setOnClickListener {
            findNavController().navigate(R.id.action_QuestParentItemFragment_to_QuestCreateFragment)
        }

        // 퀘스트 리스트 조회 observe
        questParentItemViewModel.questItemList.observe(viewLifecycleOwner) {
            mActivity.dismissLoadingDialog()
            questParentItemAdapter.submitList(it)
        }

        questParentItemViewModel.removeSuccess.observe(viewLifecycleOwner){
            mActivity.dismissLoadingDialog()
            if(it){
                questParentItemViewModel.requestQuestItemList(mainActivityViewModel.parentUser!!.id)
            }
        }


        questParentItemAdapter.itemClickListener = object : QuestParentItemAdapter.ItemClickListener{
            override fun onClick(view: View, position: Int, data: Quest) {
                mainActivityViewModel.selectedQuest = data
                openDialog(data)


            }


        }

//        questParentItemAdapter.setOnItemClickListener(object : QuestParentItemAdapter.OnItemClickListener {
//            override fun onItemClick(quest: Quest) {
//                // 아이템 클릭 시에 수행할 동작을 여기에 구현
//                // 예를 들어, fragment 전환 등을 수행할 수 있습니다.
//                val action = QuestParentItemFragmentDirections.actionQuestParentItemFragmentToYourDestinationFragment(quest.id)
//                findNavController().navigate(action)
//            }
//        })
    }

    private fun openDialog(data : Quest) = with(dialogBinding) {


        dialogBottomSheetChooseQuestQuestNameTextView.text = data.title
        dialogBottomSheetChooseQuestRewardTextView.text = StringFormatUtil.moneyToWon(data.reward)




        dialogBottomSheetChooseQuestInsertButton.setOnClickListener {
            findNavController().navigate(R.id.action_QuestParentItemFragment_to_QuestRegistFragment)
            dialog.dismiss()
        }
        dialogBottomSheetChooseQuestRemoveButton.setOnClickListener {
            questParentItemViewModel.removeQuest(data.id)
            dialog.dismiss()
            mActivity.showLoadingDialog(requireContext())
        }

        dialog.show()
    }
}
