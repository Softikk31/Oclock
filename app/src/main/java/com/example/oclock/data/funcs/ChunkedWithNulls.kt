package com.example.oclock.data.funcs

fun <I> List<I?>.chunkedWithNulls(chunked: Int): List<List<I?>> {
    if (isEmpty()) return emptyList()

    if (size % chunked == 0) return chunked(chunked)

    val mutableList = toMutableList()

    while ((mutableList.size % chunked) != 0) {
        mutableList.add(null)
    }

    return mutableList.chunked(chunked)
}