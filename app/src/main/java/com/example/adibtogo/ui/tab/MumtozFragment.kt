package com.example.adibtogo.ui.tab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.adibtogo.R
import com.example.adibtogo.adapter.AdibTogoAdapter
import com.example.adibtogo.databinding.FragmentMumtozBinding
import com.example.adibtogo.databinding.FragmentSettingsBinding
import com.example.adibtogo.model.AdibTogo
import com.example.adibtogo.uti.toast
import com.google.firebase.firestore.FirebaseFirestore


class MumtozFragment : Fragment() {
    private var _binding: FragmentMumtozBinding? = null
    private val binding get() = _binding!!
    private val adibTogoAdapter by lazy { AdibTogoAdapter() }
    private val firebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMumtozBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = adibTogoAdapter
        }

        uploadAllData()

        adibTogoAdapter.onClick = {
            val bundle = bundleOf("adib" to it)
            findNavController().navigate(R.id.action_navigation_home_to_detailFragment, bundle)
        }
    }

    private fun uploadAllData() {
        firebaseFirestore.collection("Mumtoz")
            .addSnapshotListener { success, error ->
                if (success != null) {
                    binding.progressBar.isVisible = false
                    val adibList = success.toObjects(AdibTogo::class.java)
                    adibTogoAdapter.submitList(adibList)
                } else {
                    toast(error?.message.toString())
                    binding.progressBar.isVisible = false
                }
            }
    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}