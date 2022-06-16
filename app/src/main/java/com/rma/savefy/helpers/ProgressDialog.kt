package com.rma.savefy.helpers

import android.app.Activity
import android.app.Dialog
import android.graphics.drawable.GradientDrawable
import androidx.core.content.ContextCompat
import com.rma.savefy.R
import com.rma.savefy.databinding.ProgressDialogBinding

class ProgressDialog(private val activity: Activity) {
    private lateinit var dialog: Dialog

    fun show(): Dialog {
        return show(null)
    }

    fun show(title: CharSequence?): Dialog {
        val inflater = activity.layoutInflater
        val view = ProgressDialogBinding.inflate(inflater)
        if (title != null) {
            view.progressTitle.text = title
        }

        val gradientDrawable = GradientDrawable()
        gradientDrawable.cornerRadius = 40f
        gradientDrawable.setColor(ContextCompat.getColor(activity, R.color.gray))

        dialog = Dialog(activity)
        dialog.window?.setBackgroundDrawable(gradientDrawable)
        dialog.setCancelable(false)
        dialog.setContentView(view.root)
        dialog.show()
        return dialog
    }

    fun dismiss(){
        dialog.dismiss()
    }
}