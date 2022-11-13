package ru.akhmetovdaniyar.weatherapp.presentation.activities

import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.github.matteobattilana.weather.PrecipType
import com.github.matteobattilana.weather.WeatherView
import ru.akhmetovdaniyar.weatherapp.R
import ru.akhmetovdaniyar.weatherapp.presentation.viewmodels.ActivityViewModel

class FragmentDay : Fragment() {

    private lateinit var dayViewModel: ActivityViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_day, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val temp: TextView = view.findViewById(R.id.temp)
        val desc: TextView = view.findViewById(R.id.desc)
        val iconImage : ImageView = view.findViewById((R.id.iconWeather))
        val weatherView: WeatherView = view.findViewById(R.id.weather_view)

        dayViewModel = ViewModelProvider(requireActivity())[ActivityViewModel::class.java]
        dayViewModel.weather.observe(viewLifecycleOwner) {
            try {
                weatherView.setWeatherData(PrecipType.CLEAR)
                temp.text = it.current.temp_c.toString()+" °C"
                desc.text = it.current.condition.text
                Glide.with(view.context).load(Uri.parse("https:" + it.current.condition.icon))
                    .centerCrop().into(iconImage)
                with(it.current.condition.text) {
                    when {
                        contains("солнце", ignoreCase = true) -> weatherView.setWeatherData(PrecipType.CLEAR)
                        contains("снеж", ignoreCase = true)-> weatherView.setWeatherData(PrecipType.SNOW)
                        contains("снег", ignoreCase = true)-> weatherView.setWeatherData(PrecipType.SNOW)
                        contains("дожд", ignoreCase = true) -> weatherView.setWeatherData(PrecipType.RAIN)
                    }
                }
            } catch (_:Exception){
                temp.text = "Город не"
                desc.text = "найден"
                iconImage.setImageResource(R.drawable.ic_error)
                weatherView.setWeatherData(PrecipType.CLEAR)
            }
        }
    }

    override fun onResume() {
        val weatherView: WeatherView = requireView().findViewById(R.id.weather_view)
        dayViewModel.weather.observe(viewLifecycleOwner) {
            try {
            with(it.current.condition.text) {
                when {
                    contains("солнце", ignoreCase = true) -> weatherView.setWeatherData(PrecipType.CLEAR)
                    contains("снеж", ignoreCase = true)-> weatherView.setWeatherData(PrecipType.SNOW)
                    contains("снег", ignoreCase = true)-> weatherView.setWeatherData(PrecipType.SNOW)
                    contains("дожд", ignoreCase = true) -> weatherView.setWeatherData(PrecipType.RAIN)
                }
            } } catch (_:Exception) {

        }
        }
        super.onResume()
    }
}