package com.prefin.ui.quest

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.prefin.MainActivityViewModel
import com.prefin.R
import com.prefin.config.BaseFragment
import com.prefin.databinding.FragmentQuestParentItemBinding

class QuestParentItemFragment : BaseFragment<FragmentQuestParentItemBinding>(FragmentQuestParentItemBinding::bind, R.layout.fragment_quest_parent_item) {
    private lateinit var questParentItemAdapter: QuestParentItemAdapter

    private val questParentItemViewModel by viewModels<QuestParentItemViewModel>()
    private val mainActivityViewModel by activityViewModels<MainActivityViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        questParentItemViewModel.requestQuestItemList(mainActivityViewModel.selectedChildId)
        init()
    }

    private fun init() = with(binding) {
        questParentItemAdapter = QuestParentItemAdapter(requireContext())
        fragmentQuestParentItemQuestListRecyclerView.apply {
            adapter = questParentItemAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        // 퀘스트 생성 버튼 클릭
        fragmentQuestParentItemQuestItemListImageView.setOnClickListener {
            findNavController().navigate(R.id.action_QuestParentItemFragment_to_QuestCreateFragment)
        }

        // 퀘스트 리스트 조회 observe
        questParentItemViewModel.questItemList.observe(viewLifecycleOwner) {
            questParentItemAdapter.submitList(it)
        }
    }
}
