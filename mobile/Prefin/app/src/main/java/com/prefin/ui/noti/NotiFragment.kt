package com.prefin.ui.noti

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.prefin.R
import com.prefin.config.ApplicationClass
import com.prefin.config.BaseFragment
import com.prefin.databinding.FragmentNotiBinding
import com.prefin.model.local.NotiMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NotiFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NotiFragment : BaseFragment<FragmentNotiBinding>(FragmentNotiBinding::bind, R.layout.fragment_noti) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var adapter : NotiMessageAdapter
    private lateinit var itemTouchHelper: ItemTouchHelper
    private val notiFragmentViewModel : NotiFragmentViewModel by viewModels()
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
    fun init(){

        notiFragmentViewModel.getAll()
        notiFragmentViewModel.notiList.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
        adapter = NotiMessageAdapter(requireContext(), this)
        val layoutManager = LinearLayoutManager(requireContext())
        binding.fragmentStudentNotiRecyclerView.layoutManager = layoutManager
        binding.fragmentStudentNotiRecyclerView.adapter = adapter

        val itemTouchHelperCallback = ItemTouchHelperCallback(adapter)
        itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.fragmentStudentNotiRecyclerView)

        binding.fragmentNotiBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    fun deleteMessage(data  : NotiMessage){
        try{
            CoroutineScope(Dispatchers.IO).launch {
                ApplicationClass.notiMessageDatabase.notiMessageDao.deleteNotiMessage(data)
            }
        }catch (e : Exception){
            Log.d("NOTI DELETE ERROR", "deleteMessage: ${e.message}")
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NotiFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NotiFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}