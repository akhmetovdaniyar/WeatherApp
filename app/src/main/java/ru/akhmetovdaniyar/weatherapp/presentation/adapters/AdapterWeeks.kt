package ru.akhmetovdaniyar.weatherapp.presentation.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.akhmetovdaniyar.weatherapp.R
import ru.akhmetovdaniyar.weatherapp.data.models.Forecastday

class AdapterWeeks(private val data: List<Forecastday>, private val context: Context): RecyclerView.Adapter<AdapterWeeks.MyViewHolder>() {

    class MyViewHolder(val view: View): RecyclerView.ViewHolder(view) {

            val date: TextView
            val temp: TextView
            val iconImage: ImageView
            val desc: TextView

            init {
                date = view.findViewById(R.id.textDate)
                temp = view.findViewById(R.id.textTemp)
                iconImage = view.findViewById(R.id.iconWeather)
                desc = view.findViewById(R.id.textDesc)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_recyclerview, parent, false)
            return MyViewHolder(v)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.date.text = data[position].date
            holder.temp.text = data[position].day.avgtemp_c.toString()
            Glide.with(context).load(Uri.parse("https:"+data[position].day.condition.icon)).centerCrop().into(holder.iconImage)
            holder.desc.text = data[position].day.condition.text
        }

        override fun getItemCount(): Int {
            return data.size
        }
    }