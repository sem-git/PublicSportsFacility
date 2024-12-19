package ddwu.com.mobileapp.publicsportsfacility.data

import android.graphics.Bitmap
import ddwu.com.mobileapp.publicsportsfacility.data.network.SDService
import ddwu.com.mobileapp.publicsportsfacility.data.network.Facility

class SDRepository(private val sdService: SDService) {

    suspend fun getFacilities(apiKey: String, startIndex: Int, endIndex: Int): List<Facility>? {
        return sdService.getFacilities(apiKey, startIndex, endIndex)
    }
}