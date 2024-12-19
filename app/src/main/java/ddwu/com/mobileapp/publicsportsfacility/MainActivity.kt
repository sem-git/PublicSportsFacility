package ddwu.com.mobileapp.publicsportsfacility

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import ddwu.com.mobileapp.publicsportsfacility.databinding.ActivityMainBinding
import ddwu.com.mobileapp.publicsportsfacility.ui.FacilityAdapter
import ddwu.com.mobileapp.publicsportsfacility.ui.SDViewModel
import ddwu.com.mobileapp.publicsportsfacility.ui.SDViewModelFactory

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var adapter: FacilityAdapter
    private val facilityViewModel: SDViewModel by viewModels {
        SDViewModelFactory((application as SDApplication).sdRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        adapter = FacilityAdapter()
        val layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }

        binding.rvFacilities.layoutManager = layoutManager
        binding.rvFacilities.adapter = adapter

        val sdViewModel: SDViewModel by viewModels {
            SDViewModelFactory((application as SDApplication).sdRepository)
        }

        sdViewModel.facilities.observe(this) { facilities ->
            adapter.facilities = facilities
            adapter.notifyDataSetChanged()
        }

        sdViewModel.loadFacilities(
            apiKey = BuildConfig.API_KEY,
            startIndex = 1,
            endIndex = 30
        )

        sdViewModel.facilities.observe(this) { facilities ->
            adapter.facilities = facilities
            adapter.notifyDataSetChanged()
        }

        adapter.setOnItemClickListener(object : FacilityAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val url = adapter.facilities?.get(position)?.IMGURL
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra("url", url)
                startActivity(intent)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchItem = menu?.findItem(R.id.searchBtn)
        val searchView = searchItem?.actionView as SearchView

        searchView.setQueryHint("검색어를 입력하세요.")
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrBlank()) {
                    val filteredList = facilityViewModel.facilities.value?.filter { facility ->
                        facility.MINCLASSNM.contains(newText, ignoreCase = true) ||
                                facility.PLACENM.contains(newText, ignoreCase = true) ||
                                facility.SVCNM.contains(newText, ignoreCase = true)
                    }.orEmpty()

                    adapter.facilities = filteredList
                    adapter.notifyDataSetChanged()
                } else {
                    adapter.facilities = facilityViewModel.facilities.value.orEmpty()
                    adapter.notifyDataSetChanged()
                }
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }
}