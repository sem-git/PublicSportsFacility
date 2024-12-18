package ddwu.com.mobileapp.publicsportsfacility.data

import ddwu.com.mobileapp.publicsportsfacility.data.network.FacilityService
import ddwu.com.mobileapp.publicsportsfacility.data.network.Facility

class SportsRepository(private val service: FacilityService) {

    suspend fun getFacilities(apiKey: String, startIndex: Int, endIndex: Int): List<Facility>? {
        return service.getFacilities(apiKey, startIndex, endIndex)
    }
}