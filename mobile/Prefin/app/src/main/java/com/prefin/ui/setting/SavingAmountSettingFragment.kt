package com.prefin.ui.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.prefin.MainActivityViewModel
import com.prefin.R
import com.prefin.config.BaseFragment
import com.prefin.databinding.FragmentSavingAmountSettingBinding
import com.prefin.util.StringFormatUtil

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SavingAmountSettingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SavingAmountSettingFragment : BaseFragment<FragmentSavingAmountSettingBinding>(FragmentSavingAmountSettingBinding::bind, R.layout.fragment_saving_amount_setting) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val savingAmountSettingFragmentViewModel : SavingAmountSettingFragmentViewModel by viewModels()
    private val mainActivityViewModel : MainActivityViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

    }

    fun init() {
        with(binding){

            savingAmountSettingFragmentViewModel.settingSuccess.observe(viewLifecycleOwner){
                if(it){
                    showSnackbar("변경이 완료되었습니다.")
                    findNavController().navigateUp()
                }
                else{
                    showSnackbar("변경이 정상적으로 되지 않았습니다. 다시 시도해 주세요")
                }
            }

            fragmentSavingAmountSettingCurrentAmount.text = mainActivityViewModel.parentUser?.let { StringFormatUtil.moneyToWon( it.maxSavingAmount) }

            fragmentSavingAmountSettingBack.setOnClickListener {
                findNavController().navigateUp()
            }
            fragmentSavingAmountSettingButton.setOnClickListener {
                val parentData = mainActivityViewModel.parentUser
                if (parentData != null) {
                    parentData.maxSavingAmount = fragmentSavingAmountSettingNextAmountEdit.text.toString().toInt()
                        savingAmountSettingFragmentViewModel.setSavingInterestAmount(parentData)
                }
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoanAmountSettingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SavingAmountSettingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}