package com.example.oclock.data

import com.example.oclock.R

data class DataStickers(
    val id: Int,
    val icon: Int,
    val text: String
)

fun getStickersTimer(): List<DataStickers> {
    return listOf<DataStickers>(
        DataStickers(1, R.drawable.timer_icon, "Таймер"),
        DataStickers(2, R.drawable.edit_message_icon, "Совещание"),
        DataStickers(3, R.drawable.moon_stars_icon, "Спящий режим"),
        DataStickers(4, R.drawable.dumbbell_ray_icon, "Тренировка"),
        DataStickers(5, R.drawable.hands_heart_icon, "Осознанность"),
        DataStickers(6, R.drawable.tool_box_icon, "Рабочий")
    )
}