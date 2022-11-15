package com.example.adibtogo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.adibtogo.adapter.TabLayoutAdapter
import com.example.adibtogo.databinding.FragmentAdibTogoBinding
import com.google.android.material.tabs.TabLayout

class AdibTogoFragment : Fragment() {

    private var _binding: FragmentAdibTogoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdibTogoBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tabLayoutAdapter = TabLayoutAdapter(this)

        binding.tabLayout22.addTab(binding.tabLayout22.newTab().setText("Mumtoz adabiyoti"))
        binding.tabLayout22.addTab(binding.tabLayout22.newTab().setText("O'zbek adabiyoti"))
        binding.tabLayout22.addTab(binding.tabLayout22.newTab().setText("Jahon adabiyoti"))
        binding.viewPager22.adapter = tabLayoutAdapter

        binding.tabLayout22.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.viewPager22.currentItem = tab?.position!!
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        binding.viewPager22.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tabLayout22.selectTab(binding.tabLayout22.getTabAt(position))
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}