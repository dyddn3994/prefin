package com.prefin.ui.account

import android.os.Bundle
import android.view.View
import com.prefin.R
import com.prefin.config.BaseFragment
import com.prefin.databinding.FragmentAccountBinding

class AccountFragment : BaseFragment<FragmentAccountBinding>(FragmentAccountBinding::bind, R.layout.fragment_account) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() = with(binding) {
    }
}
