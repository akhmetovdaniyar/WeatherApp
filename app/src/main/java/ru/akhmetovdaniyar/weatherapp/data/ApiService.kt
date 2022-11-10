package ru.akhmetovdaniyar.weatherapp.data

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import ru.akhmetovdaniyar.weatherapp.data.models.Forecast
import ru.akhmetovdaniyar.weatherapp.data.models.ModelWeather

interface ApiService {

    @GET("current.json")
    suspend fun getWeather (
        @Query("key") apiid: String,
        @Query("q") city: String,
        @Query("lang") lang : String,
    ): Response<ModelWeather>

    @GET("current.json")
    suspend fun getWeather (
        @Query("key") apiid: String,
        @Query("q") lat: String,
        @Query("") lon: String,
        @Query("lang") lang : String,
    ): Response<ModelWeather>

    @GET("forecast.json")
    suspend fun getForecast (
        @Query("key") apiid: String,
        @Query("q") city: String,
        @Query("days") days: String,
        @Query("lang") lang : String,
    ): Response<ModelWeather>

    @GET("forecast.json")
    suspend fun getForecast (
        @Query("key") apiid: String,
        @Query("q") lat: String,
        @Query("") lon: String,
        @Query("days") days: String,
        @Query("lang") lang : String,
    ): Response<ModelWeather>

    companion object {
        var apiService: ApiService? = null
        private const val MainServer = "https://api.weatherapi.com/v1/"

        fun getIntance(): ApiService {
            if (apiService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(MainServer)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                apiService = retrofit.create(ApiService::class.java)
            }
            return apiService!!
        }
    }
}