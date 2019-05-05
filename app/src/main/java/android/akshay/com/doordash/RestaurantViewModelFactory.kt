package android.akshay.com.doordash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * ViewModelFactory for sharing repository object to ViewModel
 */
class RestaurantViewModelFactory(private val restaurantRepository: RestaurantRepository) : ViewModelProvider.Factory  {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(RestaurantViewModel::class.java)) {
            return RestaurantViewModel(restaurantRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}