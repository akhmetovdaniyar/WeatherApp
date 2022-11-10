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
import ru.akhmetovdaniyar.weatherapp.R

import ru.akhmetovdaniyar.weatherapp.presentation.viewmodels.ActivityViewModel

class FragmentDay : Fragment() {

    private lateinit var dayViewModel: ActivityViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_day, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //val city: TextView = view.findViewById(R.id.city)
        val temp: TextView = view.findViewById(R.id.temp)
        val desc: TextView = view.findViewById(R.id.desc)
        val iconImage : ImageView = view.findViewById((R.id.iconWeather))

        dayViewModel = ViewModelProvider(requireActivity())[ActivityViewModel::class.java]
        dayViewModel.weather.observe(viewLifecycleOwner) {
            //city.text = it.location.name
            temp.text= it.current.temp_c.toString()
            desc.text = it.current.condition.text
            Glide.with(view.context).load(Uri.parse("https:"+it.current.condition.icon)).centerCrop().into(iconImage)
        }
        dayViewModel.forecast.observe(viewLifecycleOwner) {
            println(it.toString()+"")
        }
    }
}