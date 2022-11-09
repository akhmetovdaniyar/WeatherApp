package ru.akhmetovdaniyar.weatherapp.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.akhmetovdaniyar.weatherapp.R
import ru.akhmetovdaniyar.weatherapp.data.ApiService.Companion.getIntance
import ru.akhmetovdaniyar.weatherapp.presentation.viewmodels.ActivityViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var vm: ActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //viewPager2.adapter = ViewPagerAdapter()
        getIntance()

        val cityFind: EditText = findViewById(R.id.editCity)
        val city: TextView = findViewById(R.id.city)
        val temp: TextView = findViewById(R.id.temp)
        val desc: TextView = findViewById(R.id.desc)
        val today: Button = findViewById(R.id.buttonToday)
        val week: Button = findViewById(R.id.buttonWeek)



        vm = ViewModelProvider(this).get(ActivityViewModel::class.java)
        vm.weather.observe(this, Observer {
            city.text = it.location.name.toString()
            temp.text= it.current.temp_c.toString()
            desc.text = it.current.condition.text.toString()
        })

        today.setOnClickListener {
            vm.getWeather(cityFind.text.toString())
        }
    }
}