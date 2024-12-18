package ddwu.com.mobileapp.publicsportsfacility

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FacilityViewModelFactory(private val repository: SportsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FacilityViewModel::class.java)) {
            return FacilityViewModel(repository) as T
        }
        return IllegalArgumentException("Unknown ViewModel class") as T
    }
}