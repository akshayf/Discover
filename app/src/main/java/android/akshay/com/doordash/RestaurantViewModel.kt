package android.akshay.com.doordash

import android.akshay.com.doordash.model.Restaurant
import androidx.lifecycle.ViewModel
import io.reactivex.Observable

/**
 * View Model for loose coupling between activities and business logic
 */
class RestaurantViewModel(val restaurantRepository: RestaurantRepository) : ViewModel(){

    fun fetchRestaurants(lat: Double,lng: Double,offset: Int,limit: Int): Observable<List<Restaurant>> {
        return restaurantRepository.fetchRestaurants(lat, lng, offset, limit)
    }

    fun fetchRestaurantDetails(restaurantId: Int): Observable<Restaurant> {
        return restaurantRepository.fetchRestaurantDetails(restaurantId)
    }
}