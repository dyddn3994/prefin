package com.prefin.ui.setting

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.prefin.MainActivity
import com.prefin.MainActivityViewModel
import com.prefin.R
import com.prefin.config.BaseFragment
import com.prefin.databinding.FragmentSavingAmountSettingBinding
import com.prefin.util.StringFormatUtil

class SavingAmountSettingFragment : BaseFragment<FragmentSavingAmountSettingBinding>(FragmentSavingAmountSettingBinding::bind, R.layout.fragment_saving_amount_setting) {
    private val savingAmountSettingFragmentViewModel: SavingAmountSettingFragmentViewModel by viewModels()
    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()
    private lateinit var mActivity: MainActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mActivity = requireActivity() as MainActivity
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init() {
        with(binding) {
            savingAmountSettingFragmentViewModel.settingSuccess.observe(viewLifecycleOwner) {
                mActivity.dismissLoadingDialog()
                if (it) {
                    showSnackbar("변경이 완료되었습니다.")
                    findNavController().navigateUp()
                } else {
                    showSnackbar("변경이 정상적으로 되지 않았습니다. 다시 시도해 주세요")
                }
            }

            fragmentSavingAmountSettingCurrentAmount.text = mainActivityViewModel.parentUser?.let { StringFormatUtil.moneyToWon(it.maxSavingAmount) }

            fragmentSavingAmountSettingBack.setOnClickListener {
                findNavController().navigateUp()
            }
            fragmentSavingAmountSettingButton.setOnClickListener {
                if (fragmentSavingAmountSettingNextAmountEdit.text.isNullOrBlank()) {
                    showSnackbar("입력값을 확인해주세요.")
                } else {
                    val parentData = mainActivityViewModel.parentUser
                    if (parentData != null) {
                        parentData.maxSavingAmount = fragmentSavingAmountSettingNextAmountEdit.text.toString().toInt()
                        savingAmountSettingFragmentViewModel.setSavingInterestAmount(parentData)
                        mActivity.showLoadingDialog(requireContext())
                    }
                }
            }

            fragmentSavingAmountSettingNextAmountEdit.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    var amount = if (p0.isNullOrBlank()) 0 else p0.toString().toInt()

                    if (amount > mainActivityViewModel.parentUser!!.balance) {
                        amount = mainActivityViewModel.parentUser!!.balance
                        fragmentSavingAmountSettingNextAmountEdit.setText(amount.toString())
                    }
                }
                override fun afterTextChanged(p0: Editable?) {
                }
            })
        }
    }
}
