package com.example.oclock.data
import androidx.room.*
import android.content.Context
import java.util.*
import kotlin.math.abs

@Entity(tableName = "timer_entity")
data class TimerEntity(
    @PrimaryKey val id: Int = 0,
    val text: String,
    val h: String,
    val m: String,
    val s: String,
    val icon: Int
)

@Dao
interface TimerDao {
    @Query("SELECT * FROM timer_entity")
    suspend fun getAllTimers(): List<TimerEntity>

    @Insert
    suspend fun insertTimer(timer: TimerEntity)

    @Delete
    suspend fun deleteTimer(timer: TimerEntity)

    @Update
    suspend fun updateTimer(timer: TimerEntity)
}

@Database(entities = [TimerEntity::class], version = 1)
abstract class TimerDatabase : RoomDatabase() {
    abstract fun timerDao(): TimerDao

    companion object {
        @Volatile private var INSTANCE: TimerDatabase? = null

        fun getInstance(context: Context): TimerDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    TimerDatabase::class.java,
                    "timer_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}

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

fun generateUniqueId(): Int {
    return abs(UUID.randomUUID().mostSignificantBits).toInt()
}

suspend fun getTimeTimer(timerDao: TimerDao): List<TimerItems> {
    val entities = timerDao.getAllTimers()
    val timers = entities.map { TimerItems.DataTimerItem(it.id, it.text, it.h, it.m, it.s, it.icon) }
    return timers + TimerItems.AddItemsItem
}

suspend fun addItem(timerDao: TimerDao, text: String, h: String, m: String, s: String, icon: Int) {
    val newTimerId = generateUniqueId()
    val timer = TimerEntity(id = newTimerId, text = text, h = h, m = m, s = s, icon = icon)
    timerDao.insertTimer(timer)
}

suspend fun deleteItem(timerDao: TimerDao, id: Int) {
    val timer = timerDao.getAllTimers().find { it.id == id }
    if (timer != null) {
        timerDao.deleteTimer(timer)
    }
}

suspend fun refactorItem(timerDao: TimerDao, id: Int, text: String, h: String, m: String, s: String, icon: Int) {
    val timer = timerDao.getAllTimers().find { it.id == id }
    if (timer != null) {
        val updatedTimer = timer.copy(text = text, h = h, m = m, s = s, icon = icon)
        timerDao.updateTimer(updatedTimer)
    }
}