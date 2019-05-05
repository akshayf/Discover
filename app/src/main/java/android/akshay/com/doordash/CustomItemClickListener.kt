package android.akshay.com.doordash

import android.view.View

interface CustomItemClickListener {

    fun onItemClick(v: View, restaurantId: Int)
}