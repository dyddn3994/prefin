package com.prefin.ui.quest

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.prefin.R
import com.prefin.config.BaseFragment
import com.prefin.databinding.FragmentQuestParentItemBinding

class QuestParentItemFragment : BaseFragment<FragmentQuestParentItemBinding>(FragmentQuestParentItemBinding::bind, R.layout.fragment_quest_parent_item) {
    private lateinit var questParentItemAdapter: QuestParentItemAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() = with(binding) {
        questParentItemAdapter = QuestParentItemAdapter(requireContext())
        fragmentQuestParentItemQuestListRecyclerView.apply {
            adapter = questParentItemAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}
