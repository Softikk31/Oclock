package com.example.oclock.data


//@Entity(tableName = "users")
//class User {
//    @PrimaryKey(autoGenerate = true)
//    @NonNull
//    @ColumnInfo(name = "userId")
//    var id: Int = 0
//    @ColumnInfo(name = "userName")
//    var name: String? = null
//    var age: Int? = null
//
//    constructor() {}
//
//    constructor(id: Int, name: String, age: Int) {
//        this.id = id
//        this.name = name
//        this.age = age
//    }
//    constructor(name: String, age: Int) {
//        this.name = name
//        this.age = age
//    }
//}

val innerList = mutableListOf<TimerItems>()
private var lastId = 0

sealed class TimerItems {
    data class DataTimerItem(
        val id: Int,
        val text: String,
        val h: String,
        val m: String,
        val s: String,
        val icon: Int
    ) : TimerItems()
    data object AddItemsItem : TimerItems()
}

fun getTimeTimer(): MutableList<TimerItems> {
    return (innerList + TimerItems.AddItemsItem).toMutableList()
}

fun addItem(text: String, h: String, m: String, s: String, icon: Int) {
    lastId++
    innerList.add(
        TimerItems.DataTimerItem(
            id = lastId,
            text = text,
            h = h,
            m = m,
            s = s,
            icon = icon
        )
    )
}

fun deleteItem(id: Int) {
    innerList.removeAll { item ->
        if (item is TimerItems.DataTimerItem) {
            item.id == id
        } else {
            false
        }
    }
}

fun refactorItem(id: Int, text: String, h: String, m: String, s: String, icon: Int) {
    innerList.forEachIndexed { index, item ->
        if (item is TimerItems.DataTimerItem && item.id == id) {
            innerList[index] = item.copy(
                text = text,
                h = h,
                m = m,
                s = s,
                icon = icon
            )
        }
    }
}
