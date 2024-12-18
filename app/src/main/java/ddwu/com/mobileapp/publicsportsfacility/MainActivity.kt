package ddwu.com.mobileapp.publicsportsfacility

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ddwu.com.mobileapp.publicsportsfacility.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        val adapter = FacilityAdapter()
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        binding.rvFacilities.layoutManager = layoutManager
        binding.rvFacilities.adapter = adapter

        val facilityViewModel: FacilityViewModel by viewModels {
            FacilityViewModelFactory((application as FacilityApplication).fRepository)
        }

        facilityViewModel.facilities.observe(this) { facilities ->
            adapter.facilities = facilities
            adapter.notifyDataSetChanged()
        }

        facilityViewModel.loadFacilities(
            apiKey = "",
            startIndex = 1,
            endIndex = 20
        )

        facilityViewModel.facilities.observe(this) { facilities ->
            Log.d("MainActivity", "Received facilities: ${facilities.size} items")
            adapter.facilities = facilities
            adapter.notifyDataSetChanged()
        }
    }
}