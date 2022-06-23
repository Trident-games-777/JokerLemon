package bloodpressure.bloodpressureapp.bloodpressuretracke

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class LemonApplication : Application() {
    override fun onCreate() {
        super.onCreate()

    }
}