package com.example.fitness_application

import android.content.Context
import androidx.room.*
import androidx.room.Room


@Entity
data class Workouts(
        @PrimaryKey(autoGenerate = true)  val ID: Int,
        @ColumnInfo(name = "Title") val Title: String?,
        @ColumnInfo(name = "Description") val Description: String?,
        @ColumnInfo(name = "Date") val Date: String?
)

@Dao
interface WorkoutsDao {

    @Query("SELECT * FROM Workouts WHERE Title like :Title ORDER BY ID DESC")
    fun loadByTitle(Title: String): List<Workouts>

    @Insert
    fun insert(vararg workouts: Workouts)

    @Delete
    fun delete(workout: Workouts)

    @Update
    fun update(workout: Workouts)
}

@Database(entities = arrayOf(Workouts::class), version = 1)
abstract class WorkoutsDatabase : RoomDatabase() {
    abstract fun WorkoutsDao(): WorkoutsDao

}

class  DBManager {

    @Volatile
    private var INSTANCE: WorkoutsDatabase? = null

    fun getDatabase(context: Context): WorkoutsDatabase? {
        if (INSTANCE == null) {
            synchronized(WorkoutsDatabase::class.java) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            WorkoutsDatabase::class.java!!, "MyWorkouts").allowMainThreadQueries()
                            .build()
                }
            }
        }
        return INSTANCE
    }
}

