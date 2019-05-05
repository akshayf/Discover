package android.akshay.com.doordash

import android.akshay.com.doordash.model.Restaurant
import android.akshay.com.doordash.network.RestaurantRequestApi
import android.content.Context
import io.reactivex.Observable

/**
 * Repository for tossing data from local and network repositories
 */
class RestaurantRepository(context: Context) {

    val requestApi  = RetrofitClient.getRetrofitClient(context).create(RestaurantRequestApi::class.java)

    fun fetchRestaurants(lat: Double,lng: Double,offset: Int,limit: Int): Observable<List<Restaurant>> {
        return requestApi.getRestaurantData(lat, lng, offset, limit)
    }

    fun fetchRestaurantDetails(restaurantId: Int): Observable<Restaurant> {
        return requestApi.getRestaurantDetails(restaurantId)
    }
}