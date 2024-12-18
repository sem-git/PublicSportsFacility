package ddwu.com.mobileapp.publicsportsfacility

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ddwu.com.mobileapp.publicsportsfacility.SportsRepository
import ddwu.com.mobileapp.publicsportsfacility.Facility
import kotlinx.coroutines.launch

class FacilityViewModel(private val repository: SportsRepository) : ViewModel() {

    private val _facilities = MutableLiveData<List<Facility>>()
    val facilities: LiveData<List<Facility>> = _facilities

    fun loadFacilities(apiKey: String, startIndex: Int, endIndex: Int) = viewModelScope.launch {
        _facilities.value = repository.getFacilities(apiKey, startIndex, endIndex)
    }
}