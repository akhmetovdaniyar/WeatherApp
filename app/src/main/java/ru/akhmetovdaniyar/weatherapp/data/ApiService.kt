package ru.akhmetovdaniyar.weatherapp.data

import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import ru.akhmetovdaniyar.weatherapp.data.models.ModelWeather
import java.util.concurrent.TimeUnit

interface ApiService {

    @GET("current.json")
    suspend fun getWeather(
        @Query("key") apiid: String,
        @Query("q") latlon: String,
        @Query("lang") lang: String,
    ): Response<ModelWeather>

    @GET("forecast.json")
    suspend fun getForecast(
        @Query("key") apiid: String,
        @Query("q") latlon: String,
        @Query("days") days: String,
        @Query("lang") lang: String,
    ): Response<ModelWeather>

    companion object {
        var apiService: ApiService? = null
        private const val MainServer = "https://api.weatherapi.com/v1/"

        fun getInstance(): ApiService {
            val httpClient = OkHttpClient.Builder()
                .callTimeout(1, TimeUnit.MINUTES)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)

            if (apiService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(MainServer)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build()
                apiService = retrofit.create(ApiService::class.java)
            }
            return apiService!!
        }
    }
}