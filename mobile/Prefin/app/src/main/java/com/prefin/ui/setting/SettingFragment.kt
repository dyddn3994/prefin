package com.prefin.ui.setting

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.prefin.MainActivityViewModel
import com.prefin.R
import com.prefin.config.ApplicationClass
import com.prefin.config.BaseFragment
import com.prefin.databinding.FragmentSettingBinding

class SettingFragment : BaseFragment<FragmentSettingBinding>(FragmentSettingBinding::bind, R.layout.fragment_setting) {
    private val settingFragmentViewModel: SettingFragmentViewModel by viewModels()
    private val mainActivityViewModel : MainActivityViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init() {
        with(binding) {

            fragmentSettingFcmSwitch.isChecked = ApplicationClass.sharedPreferences.getBoolean("fcmFlag")

            if(ApplicationClass.sharedPreferences.getString("type") == "parent"){
                fragmentSettingLoanAmount.visibility = View.VISIBLE
                fragmentSettingMyAcoount.text = mainActivityViewModel.parentUser!!.account
                fragmentSettingMyName.text = mainActivityViewModel.parentUser!!.name
            }
            else {
                fragmentSettingLoanAmount.visibility = View.GONE
                fragmentSettingMyAcoount.text = mainActivityViewModel.selectedChild!!.account
                fragmentSettingMyName.text = mainActivityViewModel.selectedChild!!.name
            }
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
            fragmentSettingFcmSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
                // 스위치가 토글될 때 수행할 작업을 여기에 추가
                if (isChecked) {
                    // 스위치가 켜진 경우
                    // 원하는 작업 수행
                    ApplicationClass.sharedPreferences.addFCMFlag(true)


                } else {
                    // 스위치가 꺼진 경우
                    // 원하는 작업 수행

                    ApplicationClass.sharedPreferences.addFCMFlag(false)
                }
            }




        }
    }
}
