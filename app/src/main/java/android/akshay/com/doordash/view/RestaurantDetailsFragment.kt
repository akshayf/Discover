package android.akshay.com.doordash.view

import android.akshay.com.doordash.*
import android.akshay.com.doordash.model.Restaurant
import android.akshay.com.doordash.utils.Constants
import android.akshay.com.doordash.utils.Utility
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_restaurant_details.*
import kotlinx.android.synthetic.main.fragment_restaurant_details.view.*
import kotlinx.android.synthetic.main.fragment_restaurant_details.view.restaurant_progress_bar
import kotlinx.android.synthetic.main.fragment_restaurant_list.view.*

class RestaurantDetailsFragment : Fragment() {

    private lateinit var viewModel : RestaurantViewModel
    private lateinit var viewModelFactory: RestaurantViewModelFactory
    private val disposable = CompositeDisposable()
    private val TAG : String = "RestaurantDetailsFragment"

    companion object{

        fun newInstance(restaurantId : Int): RestaurantDetailsFragment{
            val noteDetailsFragment = RestaurantDetailsFragment()
            val bundle = Bundle()
            bundle.putInt(Constants.RESTAURANT_KEY, restaurantId)
            noteDetailsFragment.arguments = bundle
            return noteDetailsFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = RestaurantRepository(requireContext())
        viewModelFactory = RestaurantViewModelFactory(repository)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(RestaurantViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val fragmentView =   inflater.inflate(R.layout.fragment_restaurant_details, container, false)

        fetchRestaurantIdDetails(arguments!!.getInt(Constants.RESTAURANT_KEY, Constants.DEFAULT_RESTAURANT_ID), fragmentView)

        return fragmentView;
    }

    private fun fetchRestaurantIdDetails(restaurantId : Int, fragmentView : View){

        fragmentView.restaurant_progress_bar.visibility = View.VISIBLE

        if(Utility.hasNetwork(requireContext())) {

            disposable.add(viewModel.fetchRestaurantDetails(restaurantId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { restaurantDetails -> updateRestaurantDetails(restaurantDetails, fragmentView) },
                    { error ->
                        error.printStackTrace()
                        fragmentView.restaurant_progress_bar.visibility = View.GONE
                    }

                ))
        }else{
            Snackbar.make(fragmentView, getString(R.string.no_connection), Snackbar.LENGTH_LONG).show()
        }
    }

    private fun updateRestaurantDetails(restaurantDetails: Restaurant, fragmentView : View){

        fragmentView.restaurant_progress_bar.visibility = View.GONE

        fragmentView.restaurant_name_text_view.text = restaurantDetails.name
        fragmentView.restaurant_description_text_view.text = restaurantDetails.description
        fragmentView.restaurant_status_text_view.text = restaurantDetails.status
        fragmentView.restaurant_fee_text_view.text = String.format(getString(R.string.delivery_fee), restaurantDetails.deliveryFee)
        Glide.with(requireContext()).load(restaurantDetails.coverImgUrl).into(fragmentView.restaurant_image_view)

    }
}
