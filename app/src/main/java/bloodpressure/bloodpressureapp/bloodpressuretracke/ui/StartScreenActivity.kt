package bloodpressure.bloodpressureapp.bloodpressuretracke.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import bloodpressure.bloodpressureapp.bloodpressuretracke.R
import bloodpressure.bloodpressureapp.bloodpressuretracke.ext.go
import bloodpressure.bloodpressureapp.bloodpressuretracke.settings.Settings
import bloodpressure.bloodpressureapp.bloodpressuretracke.ui.view_models.LemonViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_screen)
        val lemonViewModel = ViewModelProvider(this)[LemonViewModel::class.java]

        if (!Settings.game(this)) {
            go()
        } else {
            if (lemonViewModel.isDataInDb()) {
                go(lemonViewModel.dataFromDb())
            } else {
                lemonViewModel.liveDataFromDb.observe(this) {
                    if (it != null) go(it)
                }
                lemonViewModel.start()
            }
        }
    }
}