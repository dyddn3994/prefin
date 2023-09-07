package com.prefin.ui.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.prefin.MainActivityViewModel
import com.prefin.R
import com.prefin.config.BaseFragment
import com.prefin.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::bind, R.layout.fragment_login) {
    private val loginViewModel: LoginViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            // 로그인 버튼 클릭 시
            fragmentLoginLoginButton.setOnClickListener {
                if (fragmentLoginIdEditText.text.isNullOrEmpty() || fragmentLoginPasswordEditText.text.isNullOrEmpty()) {
                    Toast.makeText(requireContext(), "비어 있는 부분을 모두 채우고 시도해주세요", Toast.LENGTH_SHORT).show()
                } else {
                    // 로그인 절차 진행
                    loginViewModel.login(
                        fragmentLoginIdEditText.text.toString(),
                        fragmentLoginPasswordEditText.text.toString(),
                    )
                }
            }

            // 회원가입 버튼 클릭시
            fragmentLoginSignupButton.setOnClickListener {
                findNavController().navigate(R.id.action_LoginFragment_to_SignUpFragment)
            }
        }
    }
}
