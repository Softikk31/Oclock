package com.example.oclock.data.funcs

import android.annotation.SuppressLint

@SuppressLint("NewApi")
fun <I> List<I>.chunkedWithNulls(chunked: Int): List<List<I?>> {
    if (isEmpty()) return emptyList()

    val chunkedList = chunked(chunked)

    if ((size % chunked) == 0) return chunkedList

    return (chunkedList.toMutableList() as MutableList<List<I?>>).apply {
        val lastRow = removeLast()
        add(
            (0..chunked).map {
                if (it < lastRow.size) {
                    lastRow[it]
                } else null
            }
        )
    }
}
