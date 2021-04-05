package com.example.pptest.utils

object Utils {

    fun addChar(str: String, ch: String): String {
        val position = 1
        val sb = StringBuilder(str)
        sb.insert(position, ch)
        return sb.toString()
    }

}