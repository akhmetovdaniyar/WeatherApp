package ru.akhmetovdaniyar.weatherapp.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.akhmetovdaniyar.weatherapp.R
import ru.akhmetovdaniyar.weatherapp.presentation.activities.FragmentDay
import ru.akhmetovdaniyar.weatherapp.presentation.activities.FragmentWeek

class ViewPagerAdapter(fragment: FragmentActivity): FragmentStateAdapter(fragment) {

    private val NUM_PAGES: Int = 2

    override fun getItemCount(): Int = NUM_PAGES

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FragmentDay()
            else -> FragmentWeek()
        }
    }


}
