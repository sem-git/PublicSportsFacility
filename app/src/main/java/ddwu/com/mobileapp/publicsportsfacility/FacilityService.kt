package ddwu.com.mobileapp.publicsportsfacility

import android.content.Context
import android.util.Log
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FacilityService(val context: Context) {
    private val service: ISportsAPI

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://openAPI.seoul.go.kr:8088/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(ISportsAPI::class.java)
    }

    suspend fun getFacilities(
        apiKey: String,
        startIndex: Int,
        endIndex: Int
    ): List<Facility>? {
        val facilityRoot = service.getFacilities(
            apiKey,
            "json",
            "ListPublicReservationSport",
            startIndex,
            endIndex
        )
        Log.d("FacilityService", "API Response: ${facilityRoot.ListPublicReservationSport.row}")
        return facilityRoot.ListPublicReservationSport.row
    }
}
