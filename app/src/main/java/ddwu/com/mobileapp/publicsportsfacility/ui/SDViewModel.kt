package ddwu.com.mobileapp.publicsportsfacility.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ddwu.com.mobileapp.publicsportsfacility.data.SDRepository
import ddwu.com.mobileapp.publicsportsfacility.data.network.Facility
import kotlinx.coroutines.launch

class SDViewModel(private val sdRepository: SDRepository) : ViewModel() {

    private val _facilities = MutableLiveData<List<Facility>>()
    val facilities: LiveData<List<Facility>> = _facilities

    fun loadFacilities(apiKey: String, startIndex: Int, endIndex: Int) = viewModelScope.launch {
        _facilities.value = sdRepository.getFacilities(apiKey, startIndex, endIndex)
    }
}