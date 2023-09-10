package com.prefin.ui.accountConnecting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.prefin.R
import com.prefin.config.BaseFragment
import com.prefin.databinding.FragmentChildJoinBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChildJoinFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChildJoinFragment : BaseFragment<FragmentChildJoinBinding>(FragmentChildJoinBinding::bind, R.layout.fragment_child_join) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun init() {
        binding.apply {
            fragmentChildJoinJoinButton.setOnClickListener {
                if(fragmentChildJoinIdEditText.text.isNullOrEmpty() || fragmentChildJoinPasswordEditText.text.isNullOrEmpty()){
                    Toast.makeText(requireContext(), "아이디 및 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
                }
                else if(fragmentChildJoinNameEditText.text.isNullOrEmpty()){
                    Toast.makeText(requireContext(), "자녀 이름을 입력해주세요", Toast.LENGTH_SHORT).show()
                }
                else {
                    // 등록 후 화면 이동
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
         * @return A new instance of fragment ChildJoinFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ChildJoinFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}