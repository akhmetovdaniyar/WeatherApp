package ru.akhmetovdaniyar.weatherapp.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import ru.akhmetovdaniyar.weatherapp.data.ApiService
import ru.akhmetovdaniyar.weatherapp.data.models.ModelWeather

class FragmentDayViewModel constructor(private val apiService: ApiService) : ViewModel() {

    private var job: Job? = null
    private val apiid: String = "27e8fab118f14e389c993912220911"
    private var city: String = ""
    private var days: Int = 1
    private val lang: String = "ru"
    var weather = MutableLiveData<ModelWeather>()

    fun getWeather() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = apiService.getWeather(apiid, city, lang)
            withContext(Dispatchers.Main) {
                weather.postValue(response.body())
            }
        }
    }
}