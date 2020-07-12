package com.msk.movies

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog

const val DEFAULT_FILTER = ""
const val PAGE_INDEX = 0

fun hideKeyboard(activity: Activity) {
    val view = activity.currentFocus
    if (view != null) {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun showErrorDialog(activity: Activity, msg: String) {
    val builder = AlertDialog.Builder(activity)
    builder.setMessage(msg)
    val alertDialog = builder.create()
    alertDialog.show()
}
