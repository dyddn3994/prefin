package com.prefin.ui.accountConnecting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.prefin.R
import com.prefin.config.BaseFragment
import com.prefin.databinding.FragmentChildAddBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChildAddFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChildAddFragment : BaseFragment<FragmentChildAddBinding>(FragmentChildAddBinding::bind, R.layout.fragment_child_add) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var count = 0;
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
        binding.apply {
            // 자녀 계좌 등록 화면
            fragmentChildAddChildAddButton.setOnClickListener {
                findNavController().navigate(R.id.action_ChildAddFragment_to_AccountInputChildFragment)
            }
            // 바로 부모 홈 화면
            fragmentChildAddChildCancelButton.setOnClickListener {
                findNavController().navigate(R.id.action_ChildAddFragment_to_LoginFragment)
            }
            fragmentChildAddCountTextView.text = "현재 등록된 자녀의 계정은 \n총 ${count}개 입니다."
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ChildAddFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ChildAddFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}