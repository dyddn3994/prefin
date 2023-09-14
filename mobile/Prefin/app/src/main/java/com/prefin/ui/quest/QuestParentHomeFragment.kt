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
import com.prefin.databinding.FragmentQuestParentHomeBinding
import com.prefin.model.dto.Quest
import com.prefin.model.dto.QuestOwned
import com.prefin.model.dto.QuestOwnedQuest

class QuestParentHomeFragment : BaseFragment<FragmentQuestParentHomeBinding>(FragmentQuestParentHomeBinding::bind, R.layout.fragment_quest_parent_home) {
    private lateinit var questParentAdapter: QuestParentAdapter
    private val questParentHomeFragmentViewModel : QuestParentHomeFragmentViewModel by viewModels()
    private val mainActivityViewModel : MainActivityViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        questParentHomeFragmentViewModel.getQuestList(mainActivityViewModel.selectedChild.id)
        questParentHomeFragmentViewModel.questList.observe(viewLifecycleOwner){
            questParentAdapter.submitList(it)
        }
        init()
    }

    private fun init() = with(binding) {
        questParentAdapter = QuestParentAdapter(requireContext())
        fragmentQuestParentHomeQuestListRecyclerView.apply {
            adapter = questParentAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        questParentAdapter.itemClickListener  = object : QuestParentAdapter.ItemClickListener{
            override fun onClick(view: View, position: Int, data: QuestOwnedQuest) {
                //bottomSeat 띄우기


            }


        }
        
        fragmentQuestParentHomeQuestItemListImageView.setOnClickListener {
            findNavController().navigate(R.id.action_QuestParentHomeFragment_to_QuestParentItemFragment)
        }
    }
}
