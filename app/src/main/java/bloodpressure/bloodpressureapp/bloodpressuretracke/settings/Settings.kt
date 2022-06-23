package bloodpressure.bloodpressureapp.bloodpressuretracke.settings

import android.content.Context
import android.provider.Settings
import java.io.File

object Settings {
    fun game(context: Context): Boolean {
        return game1(context) || game2()
    }

    private fun game1(context: Context): Boolean {
        return Settings.Global.getString(
            context.contentResolver,
            Settings.Global.ADB_ENABLED
        ) == "1"
    }

    private fun game2(): Boolean {
        val places = arrayOf(
            "/sbin/", "/system/bin/", "/system/xbin/",
            "/data/local/xbin/", "/data/local/bin/",
            "/system/sd/xbin/", "/system/bin/failsafe/",
            "/data/local/"
        )
        try {
            for (where in places) {
                if (File(where + "su").exists()) return true
            }
        } catch (ignore: Throwable) {
        }
        return false
    }
}