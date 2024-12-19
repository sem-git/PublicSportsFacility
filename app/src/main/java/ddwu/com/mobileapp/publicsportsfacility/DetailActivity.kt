package ddwu.com.mobileapp.publicsportsfacility

import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import ddwu.com.mobileapp.publicsportsfacility.data.network.Facility
import ddwu.com.mobileapp.publicsportsfacility.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    val detailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    private lateinit var facility: Facility

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(detailBinding.root)

        facility = intent.getSerializableExtra("facility") as Facility

        detailBinding.tvPlaceNameDetail.text = "시설명 : ${facility.PLACENM}"
        detailBinding.tvServiceNameDetail.text = "서비스명 : ${facility.SVCNM}"
        detailBinding.tvSubClassDetail.text = "분류 : ${facility.MINCLASSNM}"
        detailBinding.tvServiceStatusDetail.text = "상태 : ${facility.SVCSTATNM}"
        detailBinding.tvHoursDetail.text = "이용시간 : ${facility.V_MIN} - ${facility.V_MAX}"
        detailBinding.tvAreaNameDetail.text = "위치 : ${facility.AREANM}"
        detailBinding.tvTelNumberDetail.text = "전화번호 : ${facility.TELNO}"
        detailBinding.tvPayDetail.text = "이용 : ${facility.PAYATNM}"


        detailBinding.tvServiceURLDetail.text =
            Html.fromHtml("예약 바로가기 <br><a href=\"${facility.SVCURL}\">${facility.SVCURL}</a>")
        detailBinding.tvServiceURLDetail.movementMethod = LinkMovementMethod.getInstance()

        Glide.with(this)
            .load(facility.IMGURL)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.ic_dialog_alert)
            .into(detailBinding.ivPlaceDetail)
    }
}

