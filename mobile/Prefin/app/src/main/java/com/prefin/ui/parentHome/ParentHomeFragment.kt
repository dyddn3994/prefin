package com.prefin.ui.parentHome

import android.os.Bundle
import android.util.Log
import android.view.View

import android.view.ViewGroup

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.prefin.MainActivityViewModel
import com.prefin.R
import com.prefin.config.ApplicationClass
import com.prefin.config.BaseFragment
import com.prefin.databinding.FragmentParentHomeBinding
import com.prefin.databinding.ItemChildAccountBinding
import com.prefin.model.dto.Child

private const val TAG = "ParentHomeFragment prefin"
class ParentHomeFragment : BaseFragment<FragmentParentHomeBinding>(FragmentParentHomeBinding::bind, R.layout.fragment_parent_home) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var adpater: ChildAccountAdapter
    private val parentHomeViewModel: ParentHomeFragmentViewModel by viewModels()
    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()


    private val childList = mutableListOf<Child>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init() {
        Log.d(TAG, "init: ${ApplicationClass.sharedPreferences.getString("type")}")

        parentHomeViewModel.parent.observe(viewLifecycleOwner) {
            mainActivityViewModel.parentUser = it
            binding.fragmentParentHomeMyAccountNameTextView.text = "${it.name}님의 계좌"
            binding.fragmentParentHomeMyAccountMoneyTextView.text = "${it.balance} 원"
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

            adpater.submitList(childList) // 테스트 데이터

            // 퀘스트 화면 이동
            fragmentParentHomeQuestTextView.setOnClickListener {
                findNavController().navigate(R.id.action_ParentHomeFragment_to_ChildAccountChooseFragment)
                mainActivityViewModel.destinationFragment = 1
            }

            // 자녀 저축 내역 보기
            fragmentParentHomeSavingTextView.setOnClickListener {
                findNavController().navigate(R.id.action_ParentHomeFragment_to_ChildAccountChooseFragment)
                mainActivityViewModel.destinationFragment = 2
            }

            // 자녀 대출 내역 및 승인
            fragmentParentHomeLoanTextView.setOnClickListener {
                findNavController().navigate(R.id.action_ParentHomeFragment_to_ChildAccountChooseFragment)
                mainActivityViewModel.destinationFragment = 3
            }

            // 설정 화면
            fragmentParentHomeSettingTextView.setOnClickListener {
                findNavController().navigate(R.id.action_ParentHomeFragment_to_SettingFragment)
            }
            }
        parentHomeViewModel.getParentData()
        parentHomeViewModel.getChildData()
    }

}