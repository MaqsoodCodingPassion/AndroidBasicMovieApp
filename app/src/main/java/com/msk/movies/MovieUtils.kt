package com.msk.movies

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog

object MovieUtils {

    val MOVIE_API_KEY = "5fcbd29c"
    val DEFAULT_SEARCH_MOVIE_NAME = "friends"

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
}
