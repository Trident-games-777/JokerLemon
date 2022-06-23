package bloodpressure.bloodpressureapp.bloodpressuretracke.ext

import android.app.Activity
import android.content.Intent
import android.util.Log
import bloodpressure.bloodpressureapp.bloodpressuretracke.game.JokerLemon
import bloodpressure.bloodpressureapp.bloodpressuretracke.ui.LemonWeb

fun Activity.go(data: String) = with(Intent(this, LemonWeb::class.java)) {
    putExtra(DATA_EXTRA, data)
    startActivity(this)
}.also { finish() }

fun Activity.go() = with(Intent(this, JokerLemon::class.java)) {
    startActivity(this)
}.also { finish() }

const val DATA_EXTRA = "data"