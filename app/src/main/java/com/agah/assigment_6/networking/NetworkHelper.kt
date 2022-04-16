package com.agah.assigment_6.networking

import androidx.viewbinding.BuildConfig
import okhttp3.OkHttpClient
import com.agah.assigment_6.util.MyUrl
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Url
import java.util.concurrent.TimeUnit

class NetworkHelper {

    var userService : Services? = null

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(MyUrl.BASE_URL)
            .client((getClient()))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        userService = retrofit.create(Services::class.java)
    }

    private fun getClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.readTimeout(60, TimeUnit.SECONDS)
        httpClient.writeTimeout(60, TimeUnit.SECONDS)
        httpClient.connectTimeout(60, TimeUnit.SECONDS)
        httpClient.addInterceptor(createHttpInterceptor(BuildConfig.DEBUG))
        return httpClient.build()
    }

    private fun createHttpInterceptor(debugMode: Boolean): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        if(debugMode)
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        else
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        return httpLoggingInterceptor
    }
}