package android.akshay.com.doordash

import android.akshay.com.doordash.utils.Constants
import android.content.Context
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * RetrofitClient for network calls
 */
object RetrofitClient {

    internal fun getRetrofitClient(context: Context) : Retrofit {

        // Create a cache object
        val cacheSize = 10 * 1024 * 1024 // 10 MB
        val httpCacheDirectory = File(context.getCacheDir(), "http-cache")
        val cache = Cache(httpCacheDirectory, cacheSize.toLong())

        // create a network cache interceptor, setting the max age to 1 minute
        val networkCacheInterceptor = Interceptor { chain ->
            val response = chain.proceed(chain.request())

            var cacheControl = CacheControl.Builder()
                .maxAge(1, TimeUnit.MINUTES)
                .build()

            response.newBuilder()
                .header("Cache-Control", cacheControl.toString())
                .build()
        }

        // Create the httpClient, configure it
        // with cache, network cache interceptor and logging interceptor
        val httpClient = OkHttpClient.Builder()
            .cache(cache)
            .addNetworkInterceptor(networkCacheInterceptor)
            .build()

        // Create the Retrofit with the httpClient
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient)
            .build()

        return retrofit;
    }
}