package com.prefin.ui.parentHome

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.prefin.R
import com.prefin.config.ApplicationClass
import com.prefin.config.BaseFragment
import com.prefin.databinding.FragmentParentHomeBinding
import com.prefin.model.dto.Child

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val TAG = "ParentHomeFragment prefin"
/**
 * A simple [Fragment] subclass.
 * Use the [ParentHomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ParentHomeFragment : BaseFragment<FragmentParentHomeBinding>(FragmentParentHomeBinding::bind, R.layout.fragment_parent_home) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var adpater : ChildAccountAdapter
    private val parentHomeViewModel : ParentHomeFragmentViewModel by viewModels()
    private val childList = mutableListOf<Child>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        test()
        init()
    }

    fun test(){
        childList.add(Child(account = "123123", name = "테스트", balance = 10000))
        childList.add(Child(account = "123123", name = "테스트2", balance = 10000))
    }
    fun init(){
        Log.d(TAG, "init: ${ApplicationClass.sharedPreferences.getString("type")}")
//        parentHomeViewModel.getParentData()
        parentHomeViewModel.getChildData()

        parentHomeViewModel.parent.observe(viewLifecycleOwner){
            binding.fragmentParentHomeMyAccountNameTextView.text = "${it.name}님의 계좌"
            binding.fragmentParentHomeMyAccountMoneyTextView.text = "${it.balance} 원"
        }

        parentHomeViewModel.childs.observe(viewLifecycleOwner){
            adpater.submitList(it)
        }


        binding.apply {

            // 눌렀을 때 계좌 거래 내역 조회
            fragmentParentHomeMyAccountLinearLayout.setOnClickListener {

            }

            // adapter 연결
            adpater = ChildAccountAdapter(requireContext())
            fragmentParentHomeChildAccountRecyclerView.adapter = adpater
            val layoutManager = LinearLayoutManager(requireContext())
            fragmentParentHomeChildAccountRecyclerView.layoutManager = layoutManager
            adpater.submitList(childList) // 테스트 데이터
            // 퀘스트 화면 이동
            fragmentParentHomeQuestTextView.setOnClickListener {
                findNavController().navigate(R.id.action_ParentHomeFragment_to_QuestParentHomeFragment)
            }

            // 자녀 저축 내역 보기
            fragmentParentHomeSavingTextView.setOnClickListener {
                findNavController().navigate(R.id.action_ParentHomeFragment_to_ChildAccountChooseFragment)
            }

            // 자녀 대출 내역 및 승인
            fragmentParentHomeLoanTextView.setOnClickListener {

            }

            // 설정 화면
            fragmentParentHomeSettingTextView.setOnClickListener {
                findNavController().navigate(R.id.action_ParentHomeFragment_to_SettingFragment)
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
         * @return A new instance of fragment ParentHomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ParentHomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}