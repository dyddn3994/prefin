package com.prefin.ui.quest

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.prefin.R
import com.prefin.config.BaseFragment
import com.prefin.databinding.FragmentQuestChildHomeBinding

class QuestChildHomeFragment : BaseFragment<FragmentQuestChildHomeBinding>(FragmentQuestChildHomeBinding::bind, R.layout.fragment_quest_child_home) {
    private lateinit var questChildAdapter: QuestChildAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() = with(binding) {
        questChildAdapter = QuestChildAdapter(requireContext())
        fragmentQuestChildHomeQuestListRecyclerView.apply {
            adapter = questChildAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}
