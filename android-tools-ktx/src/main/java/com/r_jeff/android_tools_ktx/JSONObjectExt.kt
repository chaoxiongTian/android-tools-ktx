package com.r_jeff.android_tools_ktx

import org.json.JSONObject

internal fun JSONObject.toMap(): Map<String, Any> {
    val keys: Iterator<String> = this.keys()
    val map = mutableMapOf<String, Any>()
    while (keys.hasNext()) {
        val key = keys.next()
        map[key] = this.get(key)
    }
    return map
}