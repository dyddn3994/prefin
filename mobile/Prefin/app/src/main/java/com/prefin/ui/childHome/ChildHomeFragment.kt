package com.prefin.ui.childHome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.prefin.R
import com.prefin.config.BaseFragment
import com.prefin.databinding.FragmentChildHomeBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChildHomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChildHomeFragment : BaseFragment<FragmentChildHomeBinding>(FragmentChildHomeBinding::bind, R.layout.fragment_child_home) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val childHomeFragmentViewModel : ChildHomeFragmentViewModel by viewModels()
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

        childHomeFragmentViewModel.child.observe(viewLifecycleOwner){
            with(binding){

                fragmentChildHomeMyAccountMoneyTextView.text = "${it.balance} 원"
                fragmentChildHomeSavingAccountMoneyTextView.text = "${it.savingAmount} 원"
            }
        }

        with(binding) {

            //계좌 내역 조회
            fragmentChildHomeMyAccountLinearLayout.setOnClickListener {

            }

            //저축 내역 조회
            fragmentChildHomeSavingAccountLinearLayout.setOnClickListener {

            }
            fragmentChildHomeSettingImageView.setOnClickListener {
                findNavController().navigate(R.id.action_ChildHomeFragment_to_SettingFragment)
            }

            fragmentChildHomeQuizImageView.setOnClickListener {
                if(childHomeFragmentViewModel.child.value!!.isQuizSolved == true){
                    findNavController().navigate(R.id.action_ChildHomeFragment_to_QuizFragment)
                }
                else{
                    findNavController().navigate(R.id.action_ChildHomeFragment_to_QuizFragment)
                }
            }

        }
        childHomeFragmentViewModel.getChild()

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ChildHomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ChildHomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}