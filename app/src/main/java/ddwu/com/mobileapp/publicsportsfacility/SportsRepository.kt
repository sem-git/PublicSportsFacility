package ddwu.com.mobileapp.publicsportsfacility

class SportsRepository(private val service: FacilityService) {

    suspend fun getFacilities(apiKey: String, startIndex: Int, endIndex: Int): List<Facility>? {
        return service.getFacilities(apiKey, startIndex, endIndex)
    }
}