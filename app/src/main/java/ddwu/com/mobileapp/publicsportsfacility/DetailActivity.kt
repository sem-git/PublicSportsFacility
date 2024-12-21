package ddwu.com.mobileapp.publicsportsfacility

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import ddwu.com.mobileapp.publicsportsfacility.data.network.Facility
import ddwu.com.mobileapp.publicsportsfacility.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    val detailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    private lateinit var facility: Facility
    private lateinit var googleMap: GoogleMap

    private lateinit var sharedPreferences: SharedPreferences
    private var isHeartSelected = false

    private val mapReadyCallback = object : OnMapReadyCallback {
        override fun onMapReady(p0: GoogleMap) {
            googleMap = p0

            val latitude = facility.Y.toDouble()
            val longitude = facility.X.toDouble()

            val location = LatLng(latitude, longitude)

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 18f))
            googleMap.addMarker(MarkerOptions().position(location).title(facility.PLACENM))
            googleMap.setOnMapClickListener { latLng ->
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(detailBinding.root)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = "상세정보"
        }

        sharedPreferences = getSharedPreferences("HeartPreferences", Context.MODE_PRIVATE)

        val btnHeart: ImageButton = findViewById(R.id.heart)

        facility = intent.getSerializableExtra("facility") as Facility
        isHeartSelected = sharedPreferences.contains(facility.SVCID)
        if (isHeartSelected) {
            btnHeart.setImageResource(R.mipmap.heart_fill)
        } else {
            btnHeart.setImageResource(R.mipmap.heart)
        }

        btnHeart.setOnClickListener {
            isHeartSelected = !isHeartSelected
            if (isHeartSelected) {
                btnHeart.setImageResource(R.mipmap.heart_fill)
                sharedPreferences.edit().putBoolean(facility.SVCID, true).apply()
            } else {
                btnHeart.setImageResource(R.mipmap.heart)
                sharedPreferences.edit().remove(facility.SVCID).apply()
            }
        }

        facility = intent.getSerializableExtra("facility") as Facility

        Glide.with(this)
            .load(facility.IMGURL)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.ic_dialog_alert)
            .into(detailBinding.ivPlaceDetail)

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

        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(mapReadyCallback)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        val shareItem = menu?.findItem(R.id.btnShare)

        shareItem?.setOnMenuItemClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"

            val content = "시설명: ${facility.PLACENM}\n" +
                    "서비스명: ${facility.SVCNM}\n" +
                    "분류: ${facility.MINCLASSNM}\n" +
                    "이용시간 : ${facility.V_MIN} - ${facility.V_MAX}\n" +
                    "위치: ${facility.AREANM}\n" +
                    "예약 바로가기: ${facility.SVCURL}"
            intent.putExtra(Intent.EXTRA_TEXT, content)

            startActivity(Intent.createChooser(intent, "친구에게 공유하기"))
            true
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}

