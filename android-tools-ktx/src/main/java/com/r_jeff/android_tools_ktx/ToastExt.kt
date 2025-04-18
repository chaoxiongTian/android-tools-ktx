package com.r_jeff.android_tools_ktx

import android.content.Context
import android.view.Gravity
import android.widget.Toast
import androidx.annotation.StringRes
import com.r_jeff.android_tools_ktx.utils.SimplexToast

fun Context.toast(content: String, duration: Int = Toast.LENGTH_SHORT) {
    SimplexToast.show(this, content, Gravity.BOTTOM, duration)
}

fun Context.toast(@StringRes id: Int, duration: Int = Toast.LENGTH_SHORT) {
    toast(getString(id), duration)
}

fun Context.longToast(content: String) {
    toast(content, Toast.LENGTH_LONG)
}

fun Context.longToast(@StringRes id: Int) {
    toast(id, Toast.LENGTH_LONG)
}

fun Any.toast(context: Context, content: String, duration: Int = Toast.LENGTH_SHORT) {
    context.toast(content, duration)
}

fun Any.toast(context: Context, @StringRes id: Int, duration: Int = Toast.LENGTH_SHORT) {
    context.toast(id, duration)
}

fun Any.longToast(context: Context, content: String) {
    context.longToast(content)
}

fun Any.longToast(context: Context, @StringRes id: Int) {
    context.longToast(id)
}