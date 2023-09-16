package com.prefin.ui.accountConnecting

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.prefin.MainActivityViewModel
import com.prefin.R
import com.prefin.config.ApplicationClass
import com.prefin.config.BaseFragment
import com.prefin.databinding.FragmentSimplePassBinding
import com.prefin.ui.login.LoginFragment
import com.prefin.ui.saving.SavingFragment

private const val TAG = "Prefin_SimplePassFragment"
class SimplePassFragment : BaseFragment<FragmentSimplePassBinding>(FragmentSimplePassBinding::bind, R.layout.fragment_simple_pass) {
    private var numberPadRandomList = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9).shuffled()
    private val passwords = arrayListOf<Int>()

    private val mainActivityViewModel by activityViewModels<MainActivityViewModel>()
    private val simplePassViewModel by viewModels<SimplePassViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init() = with(binding) {
        shuffleNumberPad()

        fragmentSimplePassButton0.setOnClickListener {
            inputPassword(0)
        }
        fragmentSimplePassButton1.setOnClickListener {
            inputPassword(1)
        }
        fragmentSimplePassButton2.setOnClickListener {
            inputPassword(2)
        }
        fragmentSimplePassButton3.setOnClickListener {
            inputPassword(3)
        }
        fragmentSimplePassButton4.setOnClickListener {
            inputPassword(4)
        }
        fragmentSimplePassButton5.setOnClickListener {
            inputPassword(5)
        }
        fragmentSimplePassButton6.setOnClickListener {
            inputPassword(6)
        }
        fragmentSimplePassButton7.setOnClickListener {
            inputPassword(7)
        }
        fragmentSimplePassButton8.setOnClickListener {
            inputPassword(8)
        }
        fragmentSimplePassButton9.setOnClickListener {
            inputPassword(9)
        }

        fragmentSimplePassButtonErase.setOnClickListener {
            erasePassword()
        }

        fragmentSimplePassDescriptionTextView.text =
            when (mainActivityViewModel.fromFragment) {
                in listOf(AccountInputFragment::class.simpleName, LoginFragment::class.simpleName) ->
                    "등록할 간편 비밀번호를 입력하세요."
                else -> ""
            }

        simplePassViewModel.childSimplePassRegisterSuccess.observe(viewLifecycleOwner) {
            if (!it) {
                fragmentSimplePassDescriptionTextView.text = "다시 입력하세요."
                clearPasswords()
            } else {
                findNavController().navigate(R.id.action_SimplePassFragment_to_ChildHomeFragment)
            }
        }
        simplePassViewModel.parentSimplePassRegisterSuccess.observe(viewLifecycleOwner) {
            if (!it) {
                fragmentSimplePassDescriptionTextView.text = "다시 입력하세요."
                clearPasswords()
            } else {
                findNavController().navigate(R.id.action_SimplePassFragment_to_ChildAddFragment)
            }
        }
    }

    private fun inputPassword(num: Int) = with(binding) {
        if (passwords.size < 4) {
            passwords.add(numberPadRandomList[num])
            when (passwords.size) {
                1 -> fragmentSimplePassFirstTextView.text = passwords[0].toString()
                2 -> fragmentSimplePassSecondTextView.text = passwords[1].toString()
                3 -> fragmentSimplePassThirdTextView.text = passwords[2].toString()
                4 -> fragmentSimplePassFourthTextView.text = passwords[3].toString()
            }
        }

        if (passwords.size == 4) {
            when (mainActivityViewModel.fromFragment) {
                in listOf(AccountInputFragment::class.simpleName, LoginFragment::class.simpleName) -> {
                    mainActivityViewModel.tempSimplePass = passwords.joinToString("")
                    fragmentSimplePassDescriptionTextView.text = "한번 더 입력하세요."
                    shuffleNumberPad()
                    clearPasswords()
                    mainActivityViewModel.fromFragment = SimplePassFragment::class.simpleName
                }
                SimplePassFragment::class.simpleName -> {
                    Log.d(TAG, "inputPassword: ${passwords.joinToString("")} / ${mainActivityViewModel.tempSimplePass}")
                    if (passwords.joinToString("") != mainActivityViewModel.tempSimplePass) {
                        fragmentSimplePassDescriptionTextView.text = "다시 입력하세요."
                        clearPasswords()
                        shuffleNumberPad()
                    } else {
                        if (ApplicationClass.sharedPreferences.getString("type") == "child") {
                            simplePassViewModel.setChildSimplePass(
                                ApplicationClass.sharedPreferences.getLong("id"),
                                mainActivityViewModel.tempSimplePass,
                            )
                        } else {
                            simplePassViewModel.setParentSimplePass(
                                mainActivityViewModel.parentUser!!.id,
                                mainActivityViewModel.tempSimplePass,
                            )
                        }
                    }
                }
                else -> {
                    if (passwords.joinToString("") != ApplicationClass.sharedPreferences.getString("userSimplePass")) {
                        fragmentSimplePassDescriptionTextView.text = "다시 입력하세요."
                        clearPasswords()
                        shuffleNumberPad()
                    } else {
                        findNavController().navigateUp()
                    }
                }
            }
        }
    }

    private fun erasePassword() = with(binding) {
        if (passwords.size > 0) {
            when (passwords.size) {
                1 -> fragmentSimplePassFirstTextView.text = ""
                2 -> fragmentSimplePassSecondTextView.text = ""
                3 -> fragmentSimplePassThirdTextView.text = ""
                4 -> fragmentSimplePassFourthTextView.text = ""
            }
            passwords.removeLast()
        }
    }

    private fun clearPasswords() = with(binding) {
        fragmentSimplePassFirstTextView.text = ""
        fragmentSimplePassSecondTextView.text = ""
        fragmentSimplePassThirdTextView.text = ""
        fragmentSimplePassFourthTextView.text = ""
        passwords.clear()
    }

    private fun shuffleNumberPad() = with(binding) {
        numberPadRandomList = numberPadRandomList.shuffled()
        fragmentSimplePassButton0.text = numberPadRandomList[0].toString()
        fragmentSimplePassButton1.text = numberPadRandomList[1].toString()
        fragmentSimplePassButton2.text = numberPadRandomList[2].toString()
        fragmentSimplePassButton3.text = numberPadRandomList[3].toString()
        fragmentSimplePassButton4.text = numberPadRandomList[4].toString()
        fragmentSimplePassButton5.text = numberPadRandomList[5].toString()
        fragmentSimplePassButton6.text = numberPadRandomList[6].toString()
        fragmentSimplePassButton7.text = numberPadRandomList[7].toString()
        fragmentSimplePassButton8.text = numberPadRandomList[8].toString()
        fragmentSimplePassButton9.text = numberPadRandomList[9].toString()
    }
}
