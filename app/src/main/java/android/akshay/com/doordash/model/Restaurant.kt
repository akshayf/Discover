package android.akshay.com.doordash.model

import com.google.gson.annotations.SerializedName

/**
 * data class Restaurant for holding of data
 */
data class Restaurant (val id : Int, val name : String, val description : String,
                       @SerializedName("cover_img_url")val coverImgUrl : String,
                       val status : String, @SerializedName("delivery_fee")val deliveryFee : Int){
}