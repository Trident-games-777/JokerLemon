package bloodpressure.bloodpressureapp.bloodpressuretracke.di

import android.content.Context
import androidx.room.Room
import bloodpressure.bloodpressureapp.bloodpressuretracke.data.db.LemonDao
import bloodpressure.bloodpressureapp.bloodpressuretracke.data.db.LemonDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideLemonDatabase(
        @ApplicationContext context: Context
    ): LemonDao {
        val database = Room.databaseBuilder(
            context, LemonDatabase::class.java, "lemon_db.db"
        ).allowMainThreadQueries().build()
        return database.lemonDao()
    }
}