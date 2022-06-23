package bloodpressure.bloodpressureapp.bloodpressuretracke.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import bloodpressure.bloodpressureapp.bloodpressuretracke.data.models.LemonData

@Dao
interface LemonDao {
    @Query("SELECT * FROM lemon")
    fun lemonData(): LiveData<LemonData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setLemonData(data: LemonData)

    @Query("SELECT * FROM lemon LIMIT 1")
    fun data(): LemonData?
}