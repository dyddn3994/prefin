package com.prefin.ui.saving

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.prefin.MainActivityViewModel
import com.prefin.R
import com.prefin.config.BaseFragment
import com.prefin.databinding.FragmentChildAccountChooseBinding
import com.prefin.databinding.ItemChildSelectBinding
import com.prefin.model.dto.Child

class ChildAccountChooseFragment : BaseFragment<FragmentChildAccountChooseBinding>(FragmentChildAccountChooseBinding::bind, R.layout.fragment_child_account_choose) {
    private val childAccountChooseFragmentViewModel: ChildAccountChooseFragmentViewModel by viewModels()
    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()

    private lateinit var adapter: ChildSelectAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init() {
        childAccountChooseFragmentViewModel.getChildList()

        childAccountChooseFragmentViewModel.childList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.apply {
            fragmentChildAccountChooseBackButton.setOnClickListener {
                findNavController().navigateUp()
            }

            adapter = ChildSelectAdapter(requireContext()).apply {
                itemClickListener = object : ChildSelectAdapter.ItemClickListener {
                    override fun onClick(
                        binding: ItemChildSelectBinding,
                        position: Int,
                        data: Child,
                    ) {
                        mainActivityViewModel.selectedChild = data
                        when (mainActivityViewModel.destinationFragment) {
                            1 -> findNavController().navigate(R.id.action_ChildAccountChooseFragment_to_QuestParentHomeFragment)
                            2 -> findNavController().navigate(R.id.action_ChildAccountChooseFragment_to_SavingHomeFragment)
                            else -> findNavController().navigate(R.id.action_ChildAccountChooseFragment_to_LoanHomeFragment)
                        }
                    }
                }
            }
            fragmentChildAccountChooseRecyclerView.adapter = adapter
            fragmentChildAccountChooseRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
    }
}
