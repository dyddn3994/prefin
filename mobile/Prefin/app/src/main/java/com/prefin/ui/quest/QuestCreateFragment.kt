package com.prefin.ui.quest

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.prefin.MainActivity
import com.prefin.MainActivityViewModel
import com.prefin.R
import com.prefin.config.ApplicationClass
import com.prefin.config.BaseFragment
import com.prefin.databinding.FragmentQuestCreateBinding

private const val TAG = "QuestCreateFragment prefin"
class QuestCreateFragment : BaseFragment<FragmentQuestCreateBinding>(FragmentQuestCreateBinding::bind, R.layout.fragment_quest_create) {
    private val questCreateViewModel by viewModels<QuestCreateViewModel>()
    private lateinit var mActivity : MainActivity
    private val mainActivityViewModel : MainActivityViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = requireActivity() as MainActivity
        init()
    }

    private fun init() = with(binding) {
        // 뒤로가기 버튼 클릭
        fragmentQuestCreateBackButton.setOnClickListener {
            findNavController().navigateUp()
        }

        // 퀘스트 생성 버튼 클릭
        fragmentQuestCreateCreateButton.setOnClickListener {
            if (isInvalidInput()) {
                // 입력 오류
                showSnackbar("입력값을 확인해주세요.")
            } else {
                // 퀘스트 생성 요청
                questCreateViewModel.createQuest(
                    ApplicationClass.sharedPreferences.getLong("id"),
                    fragmentQuestCreateAmountEditText.text.toString().toInt(),
                    fragmentQuestCreateQuestNameEditText.text.toString(),
                )
                mActivity.showLoadingDialog(requireContext())
            }
        }

        fragmentQuestCreateAmountEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                var amount = p0.toString().toInt()

                if(amount > mainActivityViewModel.parentUser!!.balance){
                    Log.d(TAG, "onTextChanged: $amount")

                    amount = mainActivityViewModel.parentUser!!.balance
                    fragmentQuestCreateAmountEditText.setText(amount.toString())
                }




            }
            override fun afterTextChanged(p0: Editable?) {

            }
        })


        // 퀘스트 생성 observe
        questCreateViewModel.isQuestCreateSuccess.observe(viewLifecycleOwner) {
            mActivity.dismissLoadingDialog()
            if (!it) {
                // 퀘스트 생성 실패
                showSnackbar("퀘스트 생성에 실패하였습니다.")
            } else {
                // 퀘스트 생성 성공
                showSnackbar("성공적으로 퀘스트가 생성되었습니다.")
                findNavController().navigateUp()
            }
        }
    }

    private fun isInvalidInput(): Boolean = with(binding) {
        return (
            fragmentQuestCreateQuestNameEditText.text.isNullOrBlank() ||
                fragmentQuestCreateAmountEditText.text.isNullOrBlank() ||
                fragmentQuestCreateAmountEditText.text.toString().toInt() <= 0
            )
    }
}
