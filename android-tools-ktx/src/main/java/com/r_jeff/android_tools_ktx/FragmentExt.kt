package com.r_jeff.android_tools_ktx

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

inline fun <reified T : Fragment> findFragmentFromView(view: View): T? {
    val activity = view.context as? FragmentActivity ?: return null
    val fragments = activity.supportFragmentManager.fragments
    for (fragment in fragments) {
        if (fragment.view != null && fragment.requireView().isShown && fragment is T) {
            return fragment
        }
    }
    return null
}