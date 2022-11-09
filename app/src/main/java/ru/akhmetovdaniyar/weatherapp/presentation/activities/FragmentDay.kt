package ru.akhmetovdaniyar.weatherapp.presentation.activities

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import ru.akhmetovdaniyar.weatherapp.R
import ru.akhmetovdaniyar.weatherapp.presentation.viewmodels.FragmentDayViewModel

class FragmentDay : Fragment() {

    private lateinit var viewModel: FragmentDayViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_day, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var city: EditText = view.findViewById(R.id.city)
        var temp: TextView = view.findViewById(R.id.temp)
        var desc: TextView = view.findViewById(R.id.desc)

        viewModel = ViewModelProvider(this).get(FragmentDayViewModel::class.java)
        viewModel.getWeather()
    }

}