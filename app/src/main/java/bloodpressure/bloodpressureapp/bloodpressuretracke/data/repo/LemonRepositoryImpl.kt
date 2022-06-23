package bloodpressure.bloodpressureapp.bloodpressuretracke.data.repo

import androidx.lifecycle.LiveData
import bloodpressure.bloodpressureapp.bloodpressuretracke.data.db.LemonDao
import bloodpressure.bloodpressureapp.bloodpressuretracke.data.models.LemonData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LemonRepositoryImpl @Inject constructor(
    private val database: LemonDao
) : LemonRepository {
    override fun getLemonData(): LiveData<LemonData> {
        return database.lemonData()
    }

    override fun getData(): LemonData? {
        return database.data()
    }

    override suspend fun saveLemonData(data: LemonData) {
        if (getLemonData().value == null) {
            database.setLemonData(data)
        } else if (getLemonData().value!!.data.contains(DEFAULT_STRING)) {
            database.setLemonData(data)
        }
    }

    companion object {
        const val DEFAULT_STRING = "darinamonkey.xyz/leemon.php"
    }
}