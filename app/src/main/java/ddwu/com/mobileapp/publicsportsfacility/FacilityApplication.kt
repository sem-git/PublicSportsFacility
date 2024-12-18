package ddwu.com.mobileapp.publicsportsfacility

import android.app.Application
import ddwu.com.mobileapp.publicsportsfacility.data.SportsRepository
import ddwu.com.mobileapp.publicsportsfacility.data.network.FacilityService


class FacilityApplication : Application() {
    val fService by lazy {
        FacilityService(this)
    }

    val fRepository by lazy {
        SportsRepository(fService)
    }
}