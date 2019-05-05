package android.akshay.com.doordash.network

import android.akshay.com.doordash.model.Restaurant
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * API for getting restaurants
 */
interface RestaurantRequestApi {

    @GET("/v2/restaurant/?")
    fun getRestaurantData(@Query("lat") lat: Double,
                          @Query("lng") lng: Double,
                          @Query("offset") offset: Int,
                          @Query("limit") limit: Int) : Observable<List<Restaurant>>

    @GET("/v2/restaurant/{restaurant_id}/")
    fun getRestaurantDetails(@Path("restaurant_id") restaurantId : Int) : Observable<Restaurant>
}