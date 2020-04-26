package com.topjava.graduation.util

object Util {
    fun <T : Comparable<T>?> isBetweenInclusive(value: T, start: T?, end: T?): Boolean {
        return (start == null || value!! >= start) && (end == null || value!! <= end)
    }
}