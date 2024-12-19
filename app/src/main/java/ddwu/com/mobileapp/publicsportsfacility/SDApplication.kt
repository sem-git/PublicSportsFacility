package ddwu.com.mobileapp.publicsportsfacility

import android.app.Application
import ddwu.com.mobileapp.publicsportsfacility.data.SDRepository
import ddwu.com.mobileapp.publicsportsfacility.data.network.SDService


class SDApplication : Application() {
    val sdService by lazy {
        SDService(this)
    }

    val sdRepository by lazy {
        SDRepository(sdService)
    }
}