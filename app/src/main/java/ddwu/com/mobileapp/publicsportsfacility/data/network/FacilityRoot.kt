package ddwu.com.mobileapp.publicsportsfacility.data.network


data class FacilityRoot(
    val ListPublicReservationSport: ListPublicReservationSport
)

data class ListPublicReservationSport(
    val list_total_count: Int,
    val RESULT: Result,
    val row: List<Facility>
)

data class Result(
    val CODE: String,
    val MESSAGE: String
)

data class Facility(
    val serviceID: String,
    val MINCLASSNM: String,
    val SVCSTATNM: String,
    val SVCNM: String,
    val PAYATNM: String,
    val PLACENM: String,
    val SVCURL: String,
    val X: String,
    val Y: String,
    val AREANM: String?,
    val IMGURL: String?,
    val TELNO: String?,
    val V_MIN: String?,
    val V_MAX: String?,
)