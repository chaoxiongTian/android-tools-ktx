package com.r_jeff.android_tools_ktx

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes

inline val Context.screenWidth get() = resources.displayMetrics.widthPixels
inline val Context.screenHeight get() = resources.displayMetrics.heightPixels

inline val Context.isNetworkAvailable: Boolean
    get() {
        val activeNetworkInfo = connectivityManager?.activeNetworkInfo
        return activeNetworkInfo?.isConnectedOrConnecting ?: false
    }

fun Context.dp2px(value: Int): Int = (value * resources.displayMetrics.density).toInt()
fun Context.dp2px(value: Float): Int = (value * resources.displayMetrics.density).toInt()
fun Context.sp2px(value: Int): Int = (value * resources.displayMetrics.scaledDensity).toInt()
fun Context.sp2px(value: Float): Int = (value * resources.displayMetrics.scaledDensity).toInt()
fun Context.px2dp(px: Int): Float = px.toFloat() / resources.displayMetrics.density
fun Context.px2sp(px: Int): Float = px.toFloat() / resources.displayMetrics.scaledDensity
fun Context.dimen2px(@DimenRes resource: Int): Int = resources.getDimensionPixelSize(resource)
fun Context.string(@StringRes id: Int): String = getString(id)
fun Context.color(@ColorRes id: Int): Int = resources.getColor(id)
fun Context.inflateLayout(@LayoutRes layoutId: Int, parent: ViewGroup? = null, attachToRoot: Boolean = false): View =
    LayoutInflater.from(this).inflate(layoutId, parent, attachToRoot)

/**
 * 获取当前app的版本号
 */
fun Context.getAppVersion(): String {
    val manager = applicationContext.packageManager
    try {
        val info = manager.getPackageInfo(applicationContext.packageName, 0)
        if (info != null)
            return info.versionName.toString()
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }
    return ""
}

fun Context.getAppVersionCode(): Long {
    val manager = applicationContext.packageManager
    try {
        val info = manager.getPackageInfo(applicationContext.packageName, 0)
        if (info != null) {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                info.longVersionCode
            } else {
                info.versionCode.toLong()
            }
        }
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }
    return 0L
}

fun Context.targetSDk() = try {
    packageManager.getApplicationInfo(this.packageName, 0).targetSdkVersion
} catch (e: PackageManager.NameNotFoundException) {
    e.printStackTrace()
    0
}

/**
 * Get app signature by [packageName]
 */
fun Context.getAppSignature(packageName: String = this.packageName): ByteArray? {
    val packageInfo: PackageInfo =
        packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
    val signatures = packageInfo.signatures
    return signatures?.get(0)?.toByteArray()
}

/**
 * Whether the application is installed
 */
fun Context.isPackageInstalled(pkgName: String): Boolean {
    return try {
        packageManager.getPackageInfo(pkgName, 0)
        true
    } catch (e: Exception) {
        false
    }
}

