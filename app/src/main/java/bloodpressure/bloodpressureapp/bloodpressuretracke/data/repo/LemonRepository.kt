package bloodpressure.bloodpressureapp.bloodpressuretracke.data.repo

import androidx.lifecycle.LiveData
import bloodpressure.bloodpressureapp.bloodpressuretracke.data.models.LemonData

interface LemonRepository {
    fun getLemonData(): LiveData<LemonData>
    fun getData(): LemonData?
    suspend fun saveLemonData(data: LemonData)
}