package ru.akhmetovdaniyar.weatherapp.presentation.activities

import android.location.Address
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import ru.akhmetovdaniyar.weatherapp.R
import ru.akhmetovdaniyar.weatherapp.data.ApiService.Companion.getIntance
import ru.akhmetovdaniyar.weatherapp.data.DeviceLocationTracker
import ru.akhmetovdaniyar.weatherapp.presentation.adapters.ViewPagerAdapter
import ru.akhmetovdaniyar.weatherapp.presentation.viewmodels.ActivityViewModel
import java.util.Observable

class MainActivity : AppCompatActivity(), DeviceLocationTracker.DeviceLocationListener {

    private lateinit var vm: ActivityViewModel
    private lateinit var viewPager: ViewPager2
    private lateinit var deviceLocationTracker: DeviceLocationTracker
    private var currentlLat: Double = 0.0
    private var currentLng: Double = 0.0
    private var Country: String = ""
    private var cityName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewPager = findViewById(R.id.viewPager2)
        val pagerAdapter = ViewPagerAdapter(this)
        viewPager.adapter = pagerAdapter
        getIntance()
    }

    override fun onStart() {
        val cityFind: EditText = findViewById(R.id.editCity)
        val getWeatherCity: Button = findViewById(R.id.buttonFindCity)
        val getWeatherLocation: Button = findViewById(R.id.buttonLocation)
        vm = ViewModelProvider(this).get(ActivityViewModel::class.java)
        deviceLocationTracker = DeviceLocationTracker(this, this)
        getWeatherCity.setOnClickListener {
            vm.getWeather(cityFind.text.toString())
            vm.getForecast(cityFind.text.toString())
        }
        getWeatherLocation.setOnClickListener{
            vm.getWeather(String.format(".4f",currentlLat), String.format(".4f",currentLng))
            vm.getForecast(String.format(".4f",currentlLat), String.format(".4f",currentLng))
        }
        vm.weather.observe(this){
            cityFind.setText(it.location.name)
        }
        super.onStart()
    }

    override fun onDeviceLocationChanged(results: List<Address>?) {
        val currntLocation = results?.get(0);
        currntLocation?.apply {
            currentlLat = latitude
            currentLng = longitude
            Country = countryCode
            cityName = getAddressLine(0)

        }
    }
}