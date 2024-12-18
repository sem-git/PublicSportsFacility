package ddwu.com.mobileapp.publicsportsfacility.data.network

import retrofit2.http.GET
import retrofit2.http.Path

interface ISportsAPI {

    @GET("{apiKey}/{type}/{service}/{startIndex}/{endIndex}")
    suspend fun getFacilities(
        @Path("apiKey") apiKey: String,
        @Path("type") type: String,
        @Path("service") service: String,
        @Path("startIndex") startIndex: Int,
        @Path("endIndex") endIndex: Int
    ): FacilityRoot
}
