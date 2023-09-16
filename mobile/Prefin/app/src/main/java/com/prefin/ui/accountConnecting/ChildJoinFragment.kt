package com.prefin.ui.accountConnecting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.prefin.MainActivityViewModel
import com.prefin.R
import com.prefin.config.BaseFragment
import com.prefin.databinding.FragmentChildJoinBinding
import com.prefin.model.dto.Child

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

    private val childJoinFragmentViewModel : ChildJoinFragmentViewModel by viewModels()
    private val mainActivityViewModel : MainActivityViewModel by activityViewModels()

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

        childJoinFragmentViewModel.childJoinSuccess.observe(viewLifecycleOwner){
            if(it){
                mainActivityViewModel.childUser = childJoinFragmentViewModel.childUser
                findNavController().navigate(R.id.action_ChildJoinFragment_to_AccountInputChildFragment)
            }
            else{
                if(childJoinFragmentViewModel.childUser.id == -1L){
                    showSnackbar("이미 사용중인 아이디입니다.")
                }
            }
        }
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
                    val newChild = Child()
                    newChild.name = fragmentChildJoinNameEditText.text.toString()
                    newChild.userId = fragmentChildJoinIdEditText.text.toString()
                    newChild.password = fragmentChildJoinPasswordEditText.text.toString()
                    newChild.parentId = mainActivityViewModel.parentUser!!.id
                    childJoinFragmentViewModel.childJoin(newChild)
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