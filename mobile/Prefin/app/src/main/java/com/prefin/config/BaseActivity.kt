package com.prefin.config

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.BaseTransientBottomBar.ANIMATION_MODE_FADE
import com.google.android.material.snackbar.Snackbar
import com.prefin.util.LoadingDialog

//import com.petmily.R
//import com.petmily.util.NetworkUtil

// 액티비티의 기본을 작성, 뷰 바인딩 활용
abstract class BaseActivity<B : ViewBinding>(private val inflate: (LayoutInflater) -> B) :
    AppCompatActivity() {
    protected lateinit var binding: B
        private set
    lateinit var mLoadingDialog: LoadingDialog

    // 뷰 바인딩 객체를 받아서 inflate해서 화면을 만들어줌.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun showLoadingDialog(context: Context) {
        mLoadingDialog = LoadingDialog(context)
        mLoadingDialog.show()
    }
    // 띄워 놓은 로딩 다이얼로그를 없앰.
    fun dismissLoadingDialog() {
        if (mLoadingDialog.isShowing) {
            mLoadingDialog.dismiss()
        }
    }

//    fun showSnackbar(message: String) {
//        // Snackbar를 bottom navigation이 있을때는 그 위에, 없을때는 가장 아래에 띄움
//        val bottomPos =
//            if (findViewById<View>(R.id.bottom_navigation).visibility == View.VISIBLE) {
//                findViewById<View>(R.id.bottom_navigation)
//            } else {
//                null
//            }
//
//        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).apply {
//            view.setBackgroundColor(resources.getColor(R.color.main_color))
//            anchorView = bottomPos
//            animationMode = ANIMATION_MODE_FADE
//        }.show()
//    }
//
//    fun isNetworkConnected(): Boolean {
//        if (NetworkUtil.isInternetConnected(this)) return true
//
//        showSnackbar("인터넷 연결을 확인해주세요.")
//        return false
//    }
}
