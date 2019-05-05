package android.akshay.com.doordash.view

import android.akshay.com.doordash.*
import android.akshay.com.doordash.model.Restaurant
import android.akshay.com.doordash.utils.Constants
import android.akshay.com.doordash.utils.Utility
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_restaurant_list.*
import kotlinx.android.synthetic.main.fragment_restaurant_list.view.*
import androidx.recyclerview.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.fragment_restaurant_details.view.*
import kotlinx.android.synthetic.main.fragment_restaurant_list.view.restaurant_progress_bar


class RestaurantListFragment : Fragment(){

    private lateinit var restaurantRecyclerAdapter : RestaurantRecyclerAdapter
    private lateinit var viewModel : RestaurantViewModel
    private lateinit var viewModelFactory: RestaurantViewModelFactory
    private val disposable = CompositeDisposable()
    private val TAG : String = "RestaurantListFragment"

    companion object {

        fun newInstance(): RestaurantListFragment {
            return RestaurantListFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val fragmentView =  inflater.inflate(R.layout.fragment_restaurant_list, container, false)

        restaurantRecyclerAdapter = RestaurantRecyclerAdapter(requireContext(),  object : CustomItemClickListener {
            override fun onItemClick(v: View, restaurantId: Int){

                (activity as MainActivity).jumpToRestaurantDetailsFragment(restaurantId);
            }
        })


        fragmentView.restaurant_recycler_view.layoutManager = LinearLayoutManager(requireContext())
        fragmentView.restaurant_recycler_view.adapter = restaurantRecyclerAdapter
        fragmentView.restaurant_recycler_view.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))


        val repository = RestaurantRepository(requireContext())
        viewModelFactory = RestaurantViewModelFactory(repository)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(RestaurantViewModel::class.java)

        fragmentView.restaurant_progress_bar.visibility = View.VISIBLE

        if(Utility.hasNetwork(requireContext())) {
            disposable.add(viewModel.fetchRestaurants(
                Constants.latitude,
                Constants.longitude,
                Constants.offset,
                Constants.limit)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { list -> updateList(list, fragmentView) },
                    { error ->
                        error.printStackTrace()
                        fragmentView.restaurant_progress_bar.visibility = View.GONE
                    }

                ))
        }else{
            Snackbar.make(fragmentView, getString(R.string.no_connection), Snackbar.LENGTH_LONG).show()
        }

        return fragmentView
    }

    private fun updateList(list: List<Restaurant>, fragmentView: View){

        if(list.isNotEmpty()){
            restaurantRecyclerAdapter.setRestaurantList(list)
            restaurantRecyclerAdapter.notifyDataSetChanged()

        }else{
            Snackbar.make(fragmentView, getString(R.string.no_restaurants), Snackbar.LENGTH_LONG).show()
        }

        restaurant_progress_bar.visibility = View.GONE

    }

}
