package android.akshay.com.doordash.view

import android.akshay.com.doordash.*
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Main Activity that to show list of restaurants
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        jumpToRestaurantListFragment();
    }

    private fun jumpToRestaurantListFragment(){

        val fragmentManager = supportFragmentManager
        fragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, RestaurantListFragment.newInstance())
            .commit()
    }

    internal fun jumpToRestaurantDetailsFragment(restaurantId : Int){

        val fragmentManager = supportFragmentManager
        fragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragment_container, RestaurantDetailsFragment.newInstance(restaurantId))
            .commit()
    }

}
