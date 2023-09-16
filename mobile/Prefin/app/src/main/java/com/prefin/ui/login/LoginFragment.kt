package com.prefin.ui.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.prefin.MainActivityViewModel
import com.prefin.R
import com.prefin.config.ApplicationClass
import com.prefin.config.BaseFragment
import com.prefin.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::bind, R.layout.fragment_login) {
    private val loginViewModel: LoginViewModel by viewModels()
    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (ApplicationClass.sharedPreferences.getString("type") == "parent") {
            findNavController().navigate(R.id.action_LoginFragment_to_ParentHomeFragemnt)
        } else if (ApplicationClass.sharedPreferences.getString("type") == "child") {
            findNavController().navigate(R.id.action_LoginFragment_to_ChildHomeFragemnt)
        } else {
        }

        loginViewModel.loginSuccess.observe(viewLifecycleOwner) {
            if (loginViewModel.loginSuccess.value == true) {
                if (binding.fragmentLoginParentRadioButton.isChecked) {
                    findNavController().navigate(R.id.action_LoginFragment_to_ParentHomeFragemnt)
                }
                if (binding.fragmentLoginChildRadioButton.isChecked) {
                    findNavController().navigate(R.id.action_LoginFragment_to_ChildHomeFragemnt)
                }
            } else {
                showSnackbar("입력값을 확인해주세요.")
            }
        }
        binding.apply {
            // 로그인 버튼 클릭 시

            fragmentLoginLoginButton.setOnClickListener {
                if (fragmentLoginIdEditText.text.isNullOrEmpty() ||
                    fragmentLoginPasswordEditText.text.isNullOrEmpty() ||
                    !(fragmentLoginParentRadioButton.isChecked || fragmentLoginChildRadioButton.isChecked)
                ) {
                    showSnackbar("비어 있는 부분을 모두 채우고 시도해주세요")
                } else {
                    // 로그인 절차 진행
                    if (fragmentLoginChildRadioButton.isChecked) {
                        loginViewModel.childLogin(
                            fragmentLoginIdEditText.text.toString(),
                            fragmentLoginPasswordEditText.text.toString(),
                            mainActivityViewModel.fcmToken!!,
                        )
                    }
                    if (fragmentLoginParentRadioButton.isChecked) {
                        loginViewModel.parentLogin(
                            fragmentLoginIdEditText.text.toString(),
                            fragmentLoginPasswordEditText.text.toString(),
                            mainActivityViewModel.fcmToken!!,
                        )
                    }
                }
            }

            // 회원가입 버튼 클릭시
            fragmentLoginSignupButton.setOnClickListener {
                findNavController().navigate(R.id.action_LoginFragment_to_SignUpFragment)
            }
        }
    }
}
