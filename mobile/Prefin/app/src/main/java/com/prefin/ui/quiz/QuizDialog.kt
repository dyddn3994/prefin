package com.prefin.ui.quiz

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.Layout
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.prefin.R


class QuizDialog(context : Context, var isCorrected : Boolean, private val onDismissListener: OnDismissListener? = null) : Dialog(context, R.style.dialog_fullscreen) {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quiz_dialog)


        window?.setBackgroundDrawableResource(android.R.color.transparent)
        val backgroundDrawable = GradientDrawable()
        backgroundDrawable.setColor(Color.GRAY)
        window?.setBackgroundDrawable(backgroundDrawable)
        setCanceledOnTouchOutside(true)
        val image = findViewById<ImageView>(R.id.dialog_image)
        val textview = findViewById<TextView>(R.id.dialog_text_answer)
        if(isCorrected){

            image.setImageResource(R.drawable.quiz_answer_image)
            textview.text = "정답입니다!"
        }
        else{
            image.setImageResource(R.drawable.quiz_wrong_image)
            textview.text = "오답입니다!"
        }
        findViewById<View>(R.id.quiz_dialog_layout).setOnClickListener {
            dismiss()
        }
        setOnDismissListener {
            onDismissListener?.onDismiss(isCorrected)
        }


    }
    interface OnDismissListener {
        fun onDismiss(isCorrected: Boolean)
    }
}