package ru.akhmetovdaniyar.weatherapp.presentation.activities

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.fragment_day.*
import ru.akhmetovdaniyar.weatherapp.R
import ru.akhmetovdaniyar.weatherapp.data.ApiService.Companion.getInstance
import ru.akhmetovdaniyar.weatherapp.presentation.adapters.ViewPagerAdapter
import ru.akhmetovdaniyar.weatherapp.presentation.viewmodels.ActivityViewModel
import java.util.*
import java.util.Locale.Builder

class MainActivity : AppCompatActivity() {

    private var fusedLocationProvider: FusedLocationProviderClient? = null
    private val locationRequest: LocationRequest = LocationRequest.create().apply {
        interval = 30
        fastestInterval = 10
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        maxWaitTime = 60
    }

    private lateinit var vm: ActivityViewModel
    private lateinit var viewPager: ViewPager2
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var currentLat: Double = 0.0
    private var currentLng: Double = 0.0
    private var findCitylat: String = ""
    private var findCitylon: String = ""
    private var cityName: String = ""
    private var cityLat: Double = 0.0
    private var cityLng: Double = 0.0
    private var latitudeCity: String = ""
    private var longitudeCity: String = ""
    private val subString = listOf("1","2","3","4","5","6","7","8","9",",",".")

    private var locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val locationList = locationResult.locations
            if (locationList.isNotEmpty()) {
                val location = locationList.last()
                Toast.makeText(
                    this@MainActivity,
                    "Город: $location",
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.viewPager2)
        val pagerAdapter = ViewPagerAdapter(this)
        viewPager.adapter = pagerAdapter

        try {
        getInstance() } catch (_: Exception) {
            Toast.makeText(this, "Повторите попытку", Toast.LENGTH_LONG).show()
        }
        val cityFind: EditText = findViewById(R.id.editCity)
        val getWeatherCity: Button = findViewById(R.id.buttonFindCity)
        val getWeatherLocation: Button = findViewById(R.id.buttonLocation)

        vm = ViewModelProvider(this)[ActivityViewModel::class.java]

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        checkLocationPermission()

        val aLocale: Locale =
            Builder().setLanguage("RU").setScript("Cyrl").setRegion("RS").build()
        var addresses: List<Address?>
        val geocoder = Geocoder(this, aLocale)

        getWeatherCity.setOnClickListener {
            if (cityFind.text.toString() == "" || hasMatchingSubstring(cityFind.text.toString(), subString)) {
                cityFind.setText("Введите город")
            } else {
                addresses = geocoder.getFromLocationName(cityFind.text.toString(),1)
                cityLat = addresses[0]!!.latitude
                cityLng = addresses[0]!!.longitude
                latitudeCity = String.format("%.4f",cityLat).replace(',','.')
                longitudeCity = String.format("%.4f",cityLng).replace(',','.')
                vm.getWeather("$latitudeCity,$longitudeCity")
                vm.getForecast("$latitudeCity,$longitudeCity")
                try {
                    cityName = addresses[0]!!.locality
                    cityFind.setText(cityName)
                } catch (_:Exception) {
                    temp.text = "Город не"
                    desc.text = "найден"
                }
            }
        }

        getWeatherLocation.setOnClickListener{
            try {
                addresses = geocoder.getFromLocation(currentLat, currentLng, 1)
                findCitylat = String.format("%.4f",currentLat).replace(',','.')
                findCitylon = String.format("%.4f",currentLng).replace(',','.')
                vm.getWeather("$findCitylat,$findCitylon")
                vm.getForecast("$findCitylat,$findCitylon")
                cityName = addresses[0]!!.locality
                cityFind.setText(cityName)
            } catch (_:Exception) {
                temp.text = "Ошибка"
                desc.text = ""
                cityFind.setText(cityName)
                Toast.makeText(this, "Предоставьте доступ к GPS", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationProvider?.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        }
        fun getLastKnownLocation() {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location->
                    if (location != null) {
                        currentLat = location.latitude
                        currentLng = location.longitude
                    }
                }
        }
        getLastKnownLocation()
    }

    override fun onPause() {
        super.onPause()
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationProvider?.removeLocationUpdates(locationCallback)
        }
    }

    private fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                AlertDialog.Builder(this)
                    .setTitle("Требуется разрешение на местоположение")
                    .setMessage("Этому приложению требуется разрешение на определение местоположения, пожалуйста, откройте доступ определения местоположения")
                    .setPositiveButton(
                        "Понятно"
                    ) { _, _ ->
                        requestLocationPermission()
                    }
                    .create()
                    .show()
            } else {
                requestLocationPermission()
            }
        }

    }
    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
            ),
            MY_PERMISSIONS_REQUEST_LOCATION
        )
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_LOCATION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        fusedLocationProvider?.requestLocationUpdates(
                            locationRequest,
                            locationCallback,
                            Looper.getMainLooper()
                        )
                    }

                } else {
                    Toast.makeText(this, "Нет доступа к GPS", Toast.LENGTH_LONG).show()
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                    ) {
                        startActivity(
                            Intent(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", this.packageName, null),
                            ),
                        )
                    }
                }
                return
            }
        }
    }
    private fun hasMatchingSubstring(str: String, substrings: List<String>): Boolean {
        for (substring in substrings) {
            if (str.contains(substring)) {
                return true
            }
        }
        return false
    }
    companion object {
        private const val MY_PERMISSIONS_REQUEST_LOCATION = 99
    }
}