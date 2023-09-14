package com.prefin.ui.saving

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.prefin.MainActivityViewModel
import com.prefin.R
import com.prefin.config.ApplicationClass
import com.prefin.config.BaseFragment
import com.prefin.databinding.FragmentSavingHomeBinding
import com.prefin.util.StringFormatUtil

class SavingHomeFragment : BaseFragment<FragmentSavingHomeBinding>(FragmentSavingHomeBinding::bind, R.layout.fragment_saving_home) {

    private lateinit var savingHistoryAdapter: SavingHistoryAdapter
    private val mainActivityViewModel by activityViewModels<MainActivityViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() = with(binding) {
        fragmentSavingHomeBackButton.setOnClickListener {
            findNavController().navigateUp()
        }

        savingHistoryAdapter = SavingHistoryAdapter(requireContext())
        fragmentSavingHomeSavingHistoryRecyclerView.apply {
            adapter = savingHistoryAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        if (ApplicationClass.sharedPreferences.getString("type") == "parent") {
            fragmentSavingHomeSavingButton.visibility = View.GONE
            fragmentSavingHomeWithdrawButton.visibility = View.GONE
        } else {
            fragmentSavingHomeSavingButton.visibility = View.VISIBLE
            fragmentSavingHomeWithdrawButton.visibility = View.VISIBLE
        }

        fragmentSavingHomeSavingButton.setOnClickListener {
            findNavController().navigate(R.id.action_SavingHomeFragment_to_SavingFragment)
        }
        fragmentSavingHomeWithdrawButton.setOnClickListener {
            findNavController().navigate(R.id.action_SavingHomeFragment_to_WithdrawFragment)
        }

        fragmentSavingHomeSavingMoneyTextView.text = StringFormatUtil.moneyToWon(mainActivityViewModel.selectedChild.savingAmount)
    }
}
