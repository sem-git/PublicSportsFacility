package ddwu.com.mobileapp.publicsportsfacility

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ddwu.com.mobileapp.publicsportsfacility.databinding.ActivityFavoritesBinding
import ddwu.com.mobileapp.publicsportsfacility.ui.FacilityAdapter
import ddwu.com.mobileapp.publicsportsfacility.ui.SDViewModel
import ddwu.com.mobileapp.publicsportsfacility.ui.SDViewModelFactory

class FavoritesActivity : AppCompatActivity() {

    val favoriteBinding by lazy {
        ActivityFavoritesBinding.inflate(layoutInflater)
    }

    private lateinit var adapter: FacilityAdapter
    private val sdViewModel: SDViewModel by viewModels {
        SDViewModelFactory((application as SDApplication).sdRepository)
    }

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(favoriteBinding.root)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = "즐겨찾기"
        }

        adapter = FacilityAdapter()
        sharedPreferences = getSharedPreferences("HeartPreferences", Context.MODE_PRIVATE)

        val layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }

        favoriteBinding.rvFavorites.layoutManager = layoutManager
        favoriteBinding.rvFavorites.adapter = adapter

        loadFavorites()

        sdViewModel.loadFacilities(
            apiKey = BuildConfig.API_KEY,
            startIndex = 1,
            endIndex = 300
        )

        adapter.setOnItemClickListener(object : FacilityAdapter.OnItemClickListener {
            override fun onItemClick(view: android.view.View, position: Int) {
                val facility = adapter.facilities?.get(position)
                val intent =
                    android.content.Intent(this@FavoritesActivity, DetailActivity::class.java)
                intent.putExtra("facility", facility)
                startActivity(intent)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        loadFavorites()
    }

    private fun loadFavorites() {
        val favoriteIds = sharedPreferences.all.keys

        sdViewModel.facilities.observe(this) { facilities ->
            adapter.facilities = facilities.filter { it.SVCID in favoriteIds }
            adapter.notifyDataSetChanged()
        }
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