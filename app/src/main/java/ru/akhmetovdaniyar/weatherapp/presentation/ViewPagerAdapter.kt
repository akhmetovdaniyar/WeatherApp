package ru.akhmetovdaniyar.weatherapp.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.akhmetovdaniyar.weatherapp.R

class ViewPagerAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val state: Int = 2

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var city: TextView
        var temp: TextView
        var desc: TextView

        init {
            city = itemView.findViewById(R.id.city)
            temp = itemView.findViewById(R.id.temp)
            desc = itemView.findViewById(R.id.desc)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.fragment_day, parent, false))

    override fun getItemCount(): Int = state

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        null
    }
}
