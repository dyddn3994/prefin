package com.prefin.ui.quest

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.prefin.R
import com.prefin.config.BaseFragment
import com.prefin.databinding.FragmentQuestParentHomeBinding

class QuestParentHomeFragment : BaseFragment<FragmentQuestParentHomeBinding>(FragmentQuestParentHomeBinding::bind, R.layout.fragment_quest_parent_home) {
    private lateinit var questParentAdapter: QuestParentAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() = with(binding) {
        questParentAdapter = QuestParentAdapter(requireContext())
        fragmentQuestParentHomeQuestListRecyclerView.apply {
            adapter = questParentAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        
        fragmentQuestParentHomeQuestItemListImageView.setOnClickListener {
            findNavController().navigate(R.id.action_QuestParentHomeFragment_to_QuestParentItemFragment)
        }
    }
}
