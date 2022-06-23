package bloodpressure.bloodpressureapp.bloodpressuretracke.di

import bloodpressure.bloodpressureapp.bloodpressuretracke.data.repo.LemonRepository
import bloodpressure.bloodpressureapp.bloodpressuretracke.data.repo.LemonRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindLemonRepository(repository: LemonRepositoryImpl): LemonRepository
}