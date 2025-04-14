package com.r_jeff.android_tools_ktx

import java.net.URI

fun appendUrl(url: String, newQueryPairs: List<Pair<String, String>>): String {
    val queue =
            newQueryPairs.joinToString(separator = "&", transform = { it.first + "=" + it.second })
    return if (URI(url).query == null) {
        "$url?$queue"
    } else {
        "$url&$queue"
    }
}