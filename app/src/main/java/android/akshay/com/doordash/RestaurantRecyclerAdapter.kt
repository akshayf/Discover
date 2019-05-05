package android.akshay.com.doordash

import android.akshay.com.doordash.model.Restaurant
import android.akshay.com.doordash.view.MainActivity
import android.akshay.com.doordash.view.RestaurantDetailsFragment
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.recycler_view_row.view.*

/**
 * Adapter for Recycler View
 */
class RestaurantRecyclerAdapter(private val context: Context, private val customItemClickListener: CustomItemClickListener) : RecyclerView.Adapter<RestaurantRecyclerAdapter.ViewHolder>(){

    private var restaurantList : List<Restaurant> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(context).inflate(
            R.layout.recycler_view_row,
            parent,
            false)

        val viewHolder = ViewHolder(view);

        view.setOnClickListener(View.OnClickListener {
            customItemClickListener.onItemClick(it, restaurantList.get(viewHolder.adapterPosition).id)
        })

        return viewHolder;
    }

    fun setRestaurantList(restaurantList : List<Restaurant>) {
        this.restaurantList = restaurantList
    }

    override fun getItemCount(): Int {
        return restaurantList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(restaurantList.get(position), context);
    }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view){
        val tvRestaurantName = view.restaurant_name_text_view
        val tvRestaurantCategory = view.restaurant_category_text_view
        val tvRestaurantStatus = view.restaurant_status_text_view
        val tvRestaurantImage = view.restaurant_image_view

        var currentRestaurant : Restaurant? = null

        fun setData(restaurant: Restaurant, context: Context){
            tvRestaurantName?.text = restaurant.name
            tvRestaurantCategory?.text = restaurant.description
            tvRestaurantStatus?.text = restaurant.status
            Glide.with(context).load(restaurant.coverImgUrl).into(tvRestaurantImage)

            this.currentRestaurant = restaurant
        }
    }
}