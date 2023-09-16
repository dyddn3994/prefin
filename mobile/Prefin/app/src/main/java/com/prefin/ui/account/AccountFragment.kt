package com.prefin.ui.account

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.prefin.MainActivityViewModel
import com.prefin.R
import com.prefin.config.BaseFragment
import com.prefin.databinding.FragmentAccountBinding
import com.prefin.util.StringFormatUtil

class AccountFragment : BaseFragment<FragmentAccountBinding>(FragmentAccountBinding::bind, R.layout.fragment_account) {
    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() = with(binding) {
        fragmentAccountNameTextView.text = mainActivityViewModel.selectedChild.name
        fragmentAccountAccountTextView.text = mainActivityViewModel.selectedChild.account
        fragmentAccountBalanceTextView.text = StringFormatUtil.moneyToWon(mainActivityViewModel.selectedChild.balance)

        // 정기 용돈
        if (mainActivityViewModel.selectedChild.payday > 0) {
            fragmentAccountPinmoneyMonthTextView.text =
                "매월 ${mainActivityViewModel.selectedChild.payday} 일"
            fragmentAccountPinmoneyMoneyTextView.text =
                StringFormatUtil.moneyToWon(mainActivityViewModel.selectedChild.allowanceAmount)
        }
        fragmentAccountPinmoneyImageView.setOnClickListener {
            findNavController().navigate(R.id.action_AccountFragment_to_PinMoneyRegistFragment)
        }
        fragmentAccountSendPinmoneyButton.setOnClickListener {
            findNavController().navigate(R.id.action_AccountFragment_to_PinMoneySendFragment)
        }

        fragmentAccountBackButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}
