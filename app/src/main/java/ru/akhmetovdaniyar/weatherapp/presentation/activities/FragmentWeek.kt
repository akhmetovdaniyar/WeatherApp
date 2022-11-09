package ru.akhmetovdaniyar.weatherapp.presentation.activities

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.akhmetovdaniyar.weatherapp.R
import ru.akhmetovdaniyar.weatherapp.presentation.viewmodels.FragmentWeekViewModel

class FragmentWeek : Fragment() {

    private lateinit var weekViewModel: FragmentWeekViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_fragment_week, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weekViewModel = ViewModelProvider(this).get(FragmentWeekViewModel::class.java)
    }

}