package com.fernando.bookworm.extension

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import com.fernando.bookworm.R

fun Context.toastMessage(@StringRes stringRef: Int? = null, text: String? = null, isWarning: Boolean = false) {

    val toast = Toast(this)
    val view = inflate(R.layout.toast_custom)

    val textView = view.findViewById<TextView>(R.id.toast_message)
    textView.text = text ?: this.getString(stringRef!!)

    if (isWarning)
        view.setBackgroundColor(this.getColor(R.color.message_warning))

    toast.view = view
    toast.setGravity(Gravity.FILL_HORIZONTAL or Gravity.BOTTOM, 0, 0)
    toast.duration = Toast.LENGTH_LONG

    toast.show()
}

fun Context.createLoadingPopup(): AlertDialog {
    val builder = AlertDialog.Builder(this)
    val view: View = inflate(R.layout.dialog_loading)

    val text = view.findViewById<TextView>(R.id.tv_dialog_msg)
    text.setText(R.string.searching_google)
    builder.setView(view)
    builder.setCancelable(false)
    val dialog = builder.create()
    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    return dialog
}

fun Context.createBasicDialog(@StringRes stringRef: Int) {
    val builder = AlertDialog.Builder(this)
    builder.setMessage(stringRef)
    builder.setCancelable(false)
    builder.setPositiveButton(R.string.ok) { dialog, _ -> dialog.dismiss() }
    builder.create().show()
}


