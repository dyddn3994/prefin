package com.prefin.ui.login

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.prefin.MainActivity
import com.prefin.MainActivityViewModel
import com.prefin.R
import com.prefin.config.ApplicationClass
import com.prefin.config.BaseFragment
import com.prefin.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::bind, R.layout.fragment_login) {
    private val loginViewModel: LoginViewModel by viewModels()
    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()
    private lateinit var mActivity: MainActivity
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mActivity = requireActivity() as MainActivity

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // 뒤로가기 동작 수행
                    isEnabled = false
                    requireActivity().onBackPressed()
                    // 앱 종료
                    requireActivity().finish()
                }
            },
        )

        if (ApplicationClass.sharedPreferences.getString("type") == "parent") {
            findNavController().navigate(R.id.action_LoginFragment_to_ParentHomeFragemnt)
        } else if (ApplicationClass.sharedPreferences.getString("type") == "child") {
            if (ApplicationClass.sharedPreferences.getString("userSimplePass") == "") {
                mainActivityViewModel.fromFragment = LoginFragment::class.simpleName
                findNavController().navigate(R.id.action_LoginFragment_to_SimplePassFragment)
            } else {
                findNavController().navigate(R.id.action_LoginFragment_to_ChildHomeFragemnt)
            }
        } else {
        }

        loginViewModel.loginSuccess.observe(viewLifecycleOwner) {
            mActivity.dismissLoadingDialog()
            if (loginViewModel.loginSuccess.value == true) {
                ApplicationClass.sharedPreferences.addFCMFlag(true)
                if (binding.fragmentLoginParentRadioButton.isChecked) {
                    findNavController().navigate(R.id.action_LoginFragment_to_ParentHomeFragemnt)
                }
                if (binding.fragmentLoginChildRadioButton.isChecked) {
                    if (ApplicationClass.sharedPreferences.getString("userSimplePass") == "") {
                        mainActivityViewModel.fromFragment = LoginFragment::class.simpleName
                        findNavController().navigate(R.id.action_LoginFragment_to_SimplePassFragment)
                    } else {
                        findNavController().navigate(R.id.action_LoginFragment_to_ChildHomeFragemnt)
                    }
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
                    mActivity.showLoadingDialog(requireContext())
                }
            }

            // 회원가입 버튼 클릭시
            fragmentLoginSignupButton.setOnClickListener {
                findNavController().navigate(R.id.action_LoginFragment_to_SignUpFragment)
            }
        }

        val permissionListener = object : PermissionListener {
            // 권한 얻기에 성공했을 때 동작 처리
            override fun onPermissionGranted() {
            }

            // 권한 얻기에 실패했을 때 동작 처리
            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
            }
        }
        TedPermission.create()
            .setPermissionListener(permissionListener)
            .setDeniedMessage("알림 권한을 사용해주세요.")
            // 필요한 권한 설정
            .setPermissions(
                Manifest.permission.POST_NOTIFICATIONS, // targetSdk 33에서는 적용 필요
            )
            .check()
    }
}
