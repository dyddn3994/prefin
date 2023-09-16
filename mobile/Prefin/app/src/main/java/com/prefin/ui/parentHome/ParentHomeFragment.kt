package com.prefin.ui.parentHome

import android.os.Bundle
import android.util.Log
import android.view.View

import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.prefin.MainActivity
import com.prefin.MainActivityViewModel
import com.prefin.R
import com.prefin.config.ApplicationClass
import com.prefin.config.BaseFragment
import com.prefin.databinding.FragmentParentHomeBinding
import com.prefin.databinding.ItemChildAccountBinding
import com.prefin.model.dto.Child
import com.prefin.util.StringFormatUtil

private const val TAG = "ParentHomeFragment prefin"
class ParentHomeFragment : BaseFragment<FragmentParentHomeBinding>(FragmentParentHomeBinding::bind, R.layout.fragment_parent_home) {
    private lateinit var adpater: ChildAccountAdapter
    private val parentHomeViewModel: ParentHomeFragmentViewModel by viewModels()
    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()
    private lateinit var mActivity: MainActivity


    private val childList = mutableListOf<Child>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = requireActivity() as MainActivity
        if (mainActivityViewModel.parentFragmentSimplePassSuccess) {
            mainActivityViewModel.parentFragmentSimplePassSuccess = false
            findNavController().navigate(R.id.action_ParentHomeFragment_to_ChildAccountChooseFragment)
            mainActivityViewModel.destinationFragment = 1
        }
        init()
    }

    fun init() {

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                // 뒤로가기 동작 수행
                isEnabled = false
                requireActivity().onBackPressed()
                // 앱 종료
                requireActivity().finish()

            }
        })

        Log.d(TAG, "init: ${ApplicationClass.sharedPreferences.getString("type")}")

        parentHomeViewModel.parent.observe(viewLifecycleOwner) {
            mainActivityViewModel.parentUser = it
            binding.fragmentParentHomeMyAccountNameTextView.text = "${it.name}님의 계좌"
            binding.fragmentParentHomeMyAccountMoneyTextView.text = StringFormatUtil.moneyToWon(it.balance)
            mActivity.dismissLoadingDialog()
        }

        parentHomeViewModel.childs.observe(viewLifecycleOwner) {

            adpater.submitList(it)
        }

        binding.apply {

            fragmentParentHomeChildAdd.setOnClickListener {
                findNavController().navigate(R.id.action_ParentHomeFragment_to_ChildJoinFragment2)

            }
            // 눌렀을 때 계좌 거래 내역 조회
            fragmentParentHomeMyAccountLinearLayout.setOnClickListener {
            }

            // adapter 연결
            adpater = ChildAccountAdapter(requireContext()).apply {
                itemClickListener = object : ChildAccountAdapter.ItemClickListener {
                    override fun onClick(
                        binding: ItemChildAccountBinding,
                        position: Int,
                        data: Child,
                    ) {
                        mainActivityViewModel.selectedChild = data
                        findNavController().navigate(R.id.action_ParentHomeFragment_to_AccountFragment)
                    }
                }
            }
            fragmentParentHomeChildAccountRecyclerView.adapter = adpater
            fragmentParentHomeChildAccountRecyclerView.layoutManager =
                LinearLayoutManager(requireContext())

            fragmentParentHomeNotiImageView.setOnClickListener {
                findNavController().navigate(R.id.action_ParentHomeFragment_to_NotiFragment)
            }

            // 퀘스트 화면 이동
            fragmentParentHomeQuestLayout.setOnClickListener {
                mainActivityViewModel.fromFragment = ParentHomeFragment::class.simpleName
                mainActivityViewModel.parentFragmentSimplePassSuccess = true
                findNavController().navigate(R.id.action_ParentHomeFragment_to_SimplePassFragment)
            }

            // 자녀 저축 내역 보기
            fragmentParentHomeSavingLayout.setOnClickListener {
                findNavController().navigate(R.id.action_ParentHomeFragment_to_ChildAccountChooseFragment)
                mainActivityViewModel.destinationFragment = 2
            }

            // 자녀 대출 내역 및 승인
            fragmentParentHomeLoanLayout.setOnClickListener {
                findNavController().navigate(R.id.action_ParentHomeFragment_to_ChildAccountChooseFragment)
                mainActivityViewModel.destinationFragment = 3
            }

            // 설정 화면
            fragmentParentHomeSettingLayout.setOnClickListener {
                findNavController().navigate(R.id.action_ParentHomeFragment_to_SettingFragment)
             }
            }
        parentHomeViewModel.getParentData()
        parentHomeViewModel.getChildData()
        mActivity.showLoadingDialog(requireContext())

    }


}