package ddwu.com.mobileapp.publicsportsfacility.data.network

import android.content.Context
import android.graphics.Bitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.request.FutureTarget
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SDService(val context: Context) {
    private val service: IFacilitySearch

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://openAPI.seoul.go.kr:8088/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(IFacilitySearch::class.java)
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
        return facilityRoot.ListPublicReservationSport.row
    }
}
