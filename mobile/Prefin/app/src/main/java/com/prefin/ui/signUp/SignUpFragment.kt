package com.prefin.ui.signUp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.prefin.MainActivityViewModel
import com.prefin.R
import com.prefin.config.BaseFragment
import com.prefin.databinding.FragmentSignUpBinding
import java.util.regex.Pattern

class SignUpFragment : BaseFragment<FragmentSignUpBinding>(FragmentSignUpBinding::bind, R.layout.fragment_sign_up) {
    private val signUpViewModel: SignUpViewModel by viewModels()
    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signUpViewModel.signUpSuccess.observe(viewLifecycleOwner) {
            if (signUpViewModel.signUpSuccess.value == true) {
                val id = signUpViewModel.userId
                signUpViewModel.parentUser.id = id
                mainActivityViewModel.parentUser = signUpViewModel.parentUser
                findNavController().navigate(R.id.action_SignUpFragment_to_AccountInputFragment)
            }
            else {
                val id = signUpViewModel.userId
                if(id == -1L){
                    showSnackbar("이미 사용중인 아이디입니다.")
                }
            }
        }

        binding.apply {
            // 뒤로가기
            fragmentSignUpBackButton.setOnClickListener {
                findNavController().navigateUp()
            }

            // 회원가입 버튼 클릭
            fragmentSignUpRegisterButton.setOnClickListener {
                if(fragmentSignUpIdEditText.text.isNullOrEmpty()
                    || fragmentSignUpPasswordEditText.text.isNullOrEmpty()
                    || fragmentSignUpPasswordCheckEditText.text.isNullOrEmpty()
                    || fragmentSignUpNameEditText.text.isNullOrEmpty()){

                    showSnackbar("비어 있는 부분을 모두 채우고 시도해주세요")
                }
                else{
                    if( Pattern.matches("^[a-zA-Z0-9]*\$", fragmentSignUpIdEditText.text.toString()) && fragmentSignUpPasswordEditText.text.toString() == fragmentSignUpPasswordCheckEditText.text.toString()) {
                        // 회원가입 구현
                        fragmentSignUpEmailCheckTextView.visibility = View.GONE
                        fragmentSignUpPasswordConfirmTextView.visibility = View.GONE
                        fragmentSignUpPasswordCheckConfirmTextView.visibility = View.GONE

                        signUpViewModel.parentUser.userId = fragmentSignUpIdEditText.text.toString()
                        signUpViewModel.parentUser.password = fragmentSignUpPasswordCheckEditText.text.toString()
                        signUpViewModel.parentUser.name = fragmentSignUpNameEditText.text.toString()

                        signUpViewModel.signUp()
                    }
                    else if(!Pattern.matches("^[a-zA-Z0-9]*\$", fragmentSignUpIdEditText.text.toString())) {
                        fragmentSignUpEmailCheckTextView.visibility = View.VISIBLE
                    }

                    if (fragmentSignUpPasswordEditText.text.toString() != fragmentSignUpPasswordCheckEditText.text.toString()) {
                        fragmentSignUpPasswordConfirmTextView.visibility = View.VISIBLE
                        fragmentSignUpPasswordCheckConfirmTextView.visibility = View.VISIBLE
                    } else {
                        fragmentSignUpPasswordConfirmTextView.visibility = View.GONE
                        fragmentSignUpPasswordCheckConfirmTextView.visibility = View.GONE
                    }
                }
            }
        }
    }
}
