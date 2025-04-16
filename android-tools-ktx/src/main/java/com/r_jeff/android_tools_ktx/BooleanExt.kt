package com.r_jeff.android_tools_ktx

// val x = 3; val xx: Int = ((x == 3) then 3) ?: 2
infix fun <T> Boolean.then(value: T?) = if (this) value else null

// val x = 3; val xx = (x == 2).then(2, 3)
fun <T> Boolean.then(value: T, default: T) = if (this) value else default

// val x = 3; val xx = (x == 2).then({ 2 }, 3)
inline fun <T> Boolean.then(function: () -> T, default: T) = if (this) function() else default

inline fun Boolean.then(function: () -> Unit) = if (this) function() else {
}

// val x = 3; val xx = (x == 2).then({ 2 }, { 3 })
inline fun <T> Boolean.then(function: () -> T, default: () -> T) = if (this) function() else default()

//val x = 3;val xx: Int = ((x == 3) then { 2 }) ?: 2
inline infix fun <reified T> Boolean.then(function: () -> T) = if (this) function() else null

fun Boolean.str() = if (this) "1" else "0"