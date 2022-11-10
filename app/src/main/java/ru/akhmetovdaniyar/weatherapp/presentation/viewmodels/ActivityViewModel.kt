package ru.akhmetovdaniyar.weatherapp.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import ru.akhmetovdaniyar.weatherapp.data.ApiService.Companion.apiService
import ru.akhmetovdaniyar.weatherapp.data.models.Forecast
import ru.akhmetovdaniyar.weatherapp.data.models.Forecastday
import ru.akhmetovdaniyar.weatherapp.data.models.ModelWeather

class ActivityViewModel: ViewModel() {

    private var job: Job? = null
    private val apiid: String = "27e8fab118f14e389c993912220911"
    private val lang: String = "ru"
    private val days: String = "7"

    var weather = MutableLiveData<ModelWeather>()
    var forecast = MutableLiveData<ModelWeather>()

    fun getWeather(city: String) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = apiService?.getWeather(apiid, city, lang)
            withContext(Dispatchers.Main) {
                weather.postValue(response?.body())
            }
        }
    }
    fun getWeather(latitude: String, longitude: String) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = apiService?.getWeather(apiid, latitude, longitude, lang)
            withContext(Dispatchers.Main) {
                weather.postValue(response?.body())
            }
        }
    }
    fun getForecast(city: String) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = apiService?.getForecast(apiid, city, days, lang)
            withContext(Dispatchers.Main) {
                forecast.postValue(response?.body())
            }
        }
    }
    fun getForecast(latitude: String, longitude: String) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = apiService?.getForecast(apiid, latitude, longitude, days, lang)
            withContext(Dispatchers.Main) {
                forecast.postValue(response?.body())
            }
        }
    }
}