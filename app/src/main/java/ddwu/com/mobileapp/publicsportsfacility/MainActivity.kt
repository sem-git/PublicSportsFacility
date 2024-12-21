package ddwu.com.mobileapp.publicsportsfacility

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
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
    private val sdViewModel: SDViewModel by viewModels {
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

        sdViewModel.facilities.observe(this) { facilities ->
            adapter.facilities = facilities
            adapter.notifyDataSetChanged()
        }

        sdViewModel.loadFacilities(
            apiKey = BuildConfig.API_KEY,
            startIndex = 1,
            endIndex = 500
        )

        adapter.setOnItemClickListener(object : FacilityAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val facility = adapter.facilities?.get(position)
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra("facility", facility)
                startActivity(intent)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchItem = menu?.findItem(R.id.btnSearch)
        val searchView = searchItem?.actionView as SearchView

        searchView.setQueryHint("종목(시설명, 서비스명) 입력")
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrBlank()) {
                    val filteredList = sdViewModel.facilities.value?.filter { facility ->
                        facility.MINCLASSNM.contains(newText, ignoreCase = true) ||
                                facility.PLACENM.contains(newText, ignoreCase = true) ||
                                facility.SVCNM.contains(newText, ignoreCase = true)
                    }.orEmpty()

                    adapter.facilities = filteredList
                    adapter.notifyDataSetChanged()
                } else {
                    adapter.facilities = sdViewModel.facilities.value.orEmpty()
                    adapter.notifyDataSetChanged()
                }
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val builder = AlertDialog.Builder(this@MainActivity)
        when (item.itemId) {
            R.id.btnFavorites -> {
                val favoriteIntent = Intent(this, FavoritesActivity::class.java)
                startActivity(favoriteIntent)
            }

            R.id.btnFinish -> {
                builder.setTitle("앱 종료")
                    .setMessage("앱을 종료하시겠습니까?")
                    .setCancelable(false)
                    .setPositiveButton("종료") { dialogInterface, i -> finish() }
                    .setNegativeButton("취소", null)
                builder.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}