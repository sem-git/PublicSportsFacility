package ddwu.com.mobileapp.publicsportsfacility

import android.app.Application


class FacilityApplication : Application() {
    val fService by lazy {
        FacilityService(this)
    }

    val fRepository by lazy {
        SportsRepository(fService)
    }
}