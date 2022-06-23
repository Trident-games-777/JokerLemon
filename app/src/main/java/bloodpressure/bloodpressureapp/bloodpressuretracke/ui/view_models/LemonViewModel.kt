package bloodpressure.bloodpressureapp.bloodpressuretracke.ui.view_models

import android.app.Application
import android.content.res.Resources
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bloodpressure.bloodpressureapp.bloodpressuretracke.R
import bloodpressure.bloodpressureapp.bloodpressuretracke.data.models.LemonData
import bloodpressure.bloodpressureapp.bloodpressuretracke.data.repo.LemonRepository
import bloodpressure.bloodpressureapp.bloodpressuretracke.data.repo.LemonRepositoryImpl.Companion.DEFAULT_STRING
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.facebook.applinks.AppLinkData
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.onesignal.OneSignal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

@HiltViewModel
class LemonViewModel @Inject constructor(
    private val application: Application,
    private val repository: LemonRepository
) : ViewModel() {

    val liveDataFromDb: LiveData<String> =
        Transformations.map(repository.getLemonData()) { it?.data }

    fun isDataInDb(): Boolean {
        return repository.getData() != null
    }

    fun dataFromDb(): String = repository.getData()!!.data

    @Suppress("BlockingMethodInNonBlockingContext")
    fun start() {
        AppsFlyerLib.getInstance().init(
            "uzYwjd8uq5Qt4YazSCnTP7",
            object : AppsFlyerConversionListener {
                override fun onConversionDataSuccess(aData: MutableMap<String, Any>?) {
                    AppLinkData.fetchDeferredAppLinkData(application) { appLinkData ->
                        val fData = appLinkData?.targetUri.toString()
                        viewModelScope.launch {
                            val gadid = withContext(Dispatchers.Default) {
                                AdvertisingIdClient.getAdvertisingIdInfo(application).id.toString()
                            }
                            val data = "https://" + buildData(
                                res = application.resources,
                                aData = aData,
                                fData = fData,
                                uid = AppsFlyerLib.getInstance().getAppsFlyerUID(application),
                                gadid = gadid
                            )
                            tag(data1 = aData, data2 = fData)
                            repository.saveLemonData(LemonData(data = data))
                        }
                    }
                }

                override fun onConversionDataFail(p0: String?) {}
                override fun onAppOpenAttribution(p0: MutableMap<String, String>?) {}
                override fun onAttributionFailure(p0: String?) {}
            },
            application
        )
        AppsFlyerLib.getInstance().start(application)
    }

    private fun buildData(
        res: Resources,
        aData: MutableMap<String, Any>?,
        fData: String,
        uid: String,
        gadid: String
    ): String = DEFAULT_STRING.toUri().buildUpon().apply {
        appendQueryParameter(
            res.getString(R.string.secure_get_parametr),
            res.getString(R.string.secure_key)
        )
        appendQueryParameter(
            res.getString(R.string.dev_tmz_key),
            TimeZone.getDefault().id
        )
        appendQueryParameter(res.getString(R.string.gadid_key), gadid)
        appendQueryParameter(res.getString(R.string.deeplink_key), fData)
        appendQueryParameter(
            res.getString(R.string.source_key),
            aData?.get("media_source").toString()
        )
        appendQueryParameter(res.getString(R.string.af_id_key), uid)
        appendQueryParameter(
            res.getString(R.string.adset_id_key),
            aData?.get("adset_id").toString()
        )
        appendQueryParameter(
            res.getString(R.string.campaign_id_key),
            aData?.get("campaign_id").toString()
        )
        appendQueryParameter(
            res.getString(R.string.app_campaign_key),
            aData?.get("campaign").toString()
        )
        appendQueryParameter(res.getString(R.string.adset_key), aData?.get("adset").toString())
        appendQueryParameter(res.getString(R.string.adgroup_key), aData?.get("adgroup").toString())
        appendQueryParameter(
            res.getString(R.string.orig_cost_key),
            aData?.get("orig_cost").toString()
        )
        appendQueryParameter(
            res.getString(R.string.af_siteid_key),
            aData?.get("af_siteid").toString()
        )
    }.toString()

    private fun tag(data1: MutableMap<String, Any>?, data2: String) {
        OneSignal.initWithContext(application)
        OneSignal.setAppId("7bdc1f7d-9ca0-4572-8347-e1523428a630")

        val campaign = data1?.get("campaign").toString()
        val key = "key2"

        if (campaign == "null" && data2 == "null") {
            OneSignal.sendTag(key, "organic")
        } else if (data2 != "null") {
            OneSignal.sendTag(key, data2.replace("myapp://", "").substringBefore("/"))
        } else if (campaign != "null") {
            OneSignal.sendTag(key, campaign.substringBefore("_"))
        }
    }
}