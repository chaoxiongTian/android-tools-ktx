package com.r_jeff.android_tools_ktx

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun nowTimeStamp() = timestamp2DataStr(System.currentTimeMillis())

fun timestamp2DataStr(timestamp: Long): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val formattedDate: String = sdf.format(Date(timestamp)) // 将时间戳转换为Date对象并格式化
    return formattedDate
}