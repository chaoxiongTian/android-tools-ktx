package com.r_jeff.android_tools_ktx

/**
 * if [String.isNullOrEmpty], invoke f()
 * otherwise invoke t()
 */
fun <T> String?.notNull(f: () -> T, t: () -> T): T {
    return if (isNullOrEmpty()) f() else t()
}

fun <T> String?.notNull(f: () -> T, t: T): T {
    return if (isNullOrEmpty()) f() else t
}

fun String?.doIfNotEmpty(f: (String) -> Unit) {
    if (!isNullOrEmpty()) {
        f.invoke(this)
    }
}

fun String?.doCondition(f: (String) -> Unit, t: () -> Unit) {
    if (!isNullOrEmpty()) {
        f.invoke(this)
    } else {
        t.invoke()
    }
}

/**
 * whether string only contains digits
 */
fun String.areDigitsOnly() = matches(Regex("[0-9]+"))

fun String.isOpen() = this == "1"