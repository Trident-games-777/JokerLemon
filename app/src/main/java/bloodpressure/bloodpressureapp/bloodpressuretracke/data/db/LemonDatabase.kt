package bloodpressure.bloodpressureapp.bloodpressuretracke.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import bloodpressure.bloodpressureapp.bloodpressuretracke.data.models.LemonData

@Database(entities = [LemonData::class], version = 1)
abstract class LemonDatabase : RoomDatabase() {
    abstract fun lemonDao(): LemonDao
}