package android.akshay.com.doordash

import android.akshay.com.doordash.model.Restaurant
import io.reactivex.Maybe
import io.reactivex.Observable
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito
import org.mockito.Mockito.`when`

@RunWith(JUnit4::class)
class RestaurantViewModelUnitTest {

    private lateinit var viewModel : RestaurantViewModel

    @Before
    fun setUp(){

        viewModel = Mockito.mock(RestaurantViewModel::class.java)
    }

    @Test
    fun testRestaurantDetails() {

        `when`(viewModel.fetchRestaurantDetails(any(Int::class.java)))
            .thenAnswer { return@thenAnswer Maybe.just(ArgumentMatchers.any<Restaurant>())}

        val details = viewModel.fetchRestaurantDetails(0);
        assertNotNull(details)

    }

    @Test
    fun testListOfRestaurant() {

        `when`(viewModel.fetchRestaurants(
            ArgumentMatchers.anyDouble(), ArgumentMatchers.anyDouble(),
            ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt()))
            .thenAnswer { return@thenAnswer Maybe.just(ArgumentMatchers.anyList<Restaurant>())}

        val restaurants = viewModel.fetchRestaurantDetails(0);
        assertNotNull(restaurants)

    }
}
