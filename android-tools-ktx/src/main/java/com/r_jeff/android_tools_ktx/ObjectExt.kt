package com.r_jeff.android_tools_ktx


inline fun <T : Any> T.TAG() = this::class.simpleName + "Log"

inline fun <T : Any> T?.isNull() = (this == null)

inline fun <T> Any?.notNull(f: () -> T, t: () -> T): T = (this != null).then(f(), t())

inline fun <T> Any?.notNull(f: () -> T, t: T): T = (this != null).then(f(), t)

inline fun nowSec(): Long = System.currentTimeMillis() / 1000

inline fun nowMil(): Long = System.currentTimeMillis()

inline fun oneDayMil(): Long = 1000 * 24 * 60 * 60
inline fun oneHourMil(): Long = 1000 * 60 * 60
inline fun oneMinuteMil(): Long = 1000 * 60
