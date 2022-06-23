package bloodpressure.bloodpressureapp.bloodpressuretracke.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lemon")
data class LemonData(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    val data: String
)
