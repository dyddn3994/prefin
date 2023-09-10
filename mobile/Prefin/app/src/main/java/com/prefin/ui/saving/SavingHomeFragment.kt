package com.prefin.ui.saving

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.prefin.R
import com.prefin.config.BaseFragment
import com.prefin.databinding.FragmentSavingHomeBinding

class SavingHomeFragment : BaseFragment<FragmentSavingHomeBinding>(FragmentSavingHomeBinding::bind, R.layout.fragment_saving_home) {

    private lateinit var savingHistoryAdapter: SavingHistoryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() = with(binding) {
        savingHistoryAdapter = SavingHistoryAdapter(requireContext())
        fragmentSavingHomeSavingHistoryRecyclerView.apply {
            adapter = savingHistoryAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}
