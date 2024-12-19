package ddwu.com.mobileapp.publicsportsfacility.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ddwu.com.mobileapp.publicsportsfacility.data.SDRepository

class SDViewModelFactory(private val sdRepository: SDRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SDViewModel::class.java)) {
            return SDViewModel(sdRepository) as T
        }
        return IllegalArgumentException("Unknown ViewModel class") as T
    }
}