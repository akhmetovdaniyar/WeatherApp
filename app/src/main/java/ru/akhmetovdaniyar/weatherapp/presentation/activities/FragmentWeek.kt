package ru.akhmetovdaniyar.weatherapp.presentation.activities

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.akhmetovdaniyar.weatherapp.R
import ru.akhmetovdaniyar.weatherapp.data.models.Forecastday
import ru.akhmetovdaniyar.weatherapp.presentation.adapters.AdapterWeeks
import ru.akhmetovdaniyar.weatherapp.presentation.viewmodels.ActivityViewModel

class FragmentWeek : Fragment() {

    private lateinit var weekViewModel: ActivityViewModel
    var forecastDays: List<Forecastday> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_week, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weekViewModel = ViewModelProvider(requireActivity())[ActivityViewModel::class.java]
        val recyclerView : RecyclerView = view.findViewById(R.id.recyclerview)
        recyclerView.setHasFixedSize(true);
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        weekViewModel.forecast.observe(viewLifecycleOwner) {
            forecastDays = it.forecast.forecastday
            recyclerView.adapter?.notifyDataSetChanged()
            recyclerView.adapter = AdapterWeeks(forecastDays, view.context)

        }

    }
}