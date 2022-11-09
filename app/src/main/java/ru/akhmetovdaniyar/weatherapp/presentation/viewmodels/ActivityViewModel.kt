package ru.akhmetovdaniyar.weatherapp.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import ru.akhmetovdaniyar.weatherapp.data.ApiService.Companion.apiService
import ru.akhmetovdaniyar.weatherapp.data.models.ModelWeather

class ActivityViewModel: ViewModel() {

    private var job: Job? = null
    private val apiid: String = "27e8fab118f14e389c993912220911"
    private val lang: String = "ru"
    //var city: String = "London"
    private var days: Int = 1
    var weather = MutableLiveData<ModelWeather>()

    fun getWeather(city: String = "Москва") {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = apiService?.getWeather(apiid, city, lang)
            withContext(Dispatchers.Main) {
                weather.postValue(response?.body())
            }
        }
    }
}