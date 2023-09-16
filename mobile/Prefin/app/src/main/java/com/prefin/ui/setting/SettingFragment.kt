package com.prefin.ui.setting

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.prefin.R
import com.prefin.config.BaseFragment
import com.prefin.databinding.FragmentSettingBinding

class SettingFragment : BaseFragment<FragmentSettingBinding>(FragmentSettingBinding::bind, R.layout.fragment_setting) {
    private val settingFragmentViewModel: SettingFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init() {
        with(binding) {
            fragmentSettingLogout.setOnClickListener {
                settingFragmentViewModel.logout()
                findNavController().navigate(R.id.action_SettingFragment_to_LoginFragment)
            }
            fragmentSettingBack.setOnClickListener {
                findNavController().navigateUp()
            }
            fragmentSettingLoanAmount.setOnClickListener {
                findNavController().navigate(R.id.action_SettingFragment_to_SavingAmountSettingFragment)
            }
        }
    }
}
