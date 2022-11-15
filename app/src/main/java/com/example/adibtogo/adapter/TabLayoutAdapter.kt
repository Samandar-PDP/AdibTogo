package com.example.adibtogo.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.adibtogo.ui.tab.*

class TabLayoutAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MumtozFragment()
            1 -> UzbekFragment()
            2 -> JahonFragment()
            else -> Fragment()
        }
    }
}