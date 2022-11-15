package com.example.adibtogo.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.adibtogo.R
import com.example.adibtogo.databinding.FragmentAddAdibBinding
import com.example.adibtogo.model.AdibTogo
import com.example.adibtogo.uti.toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class AddAdibFragment : Fragment() {
    private var _binding: FragmentAddAdibBinding? = null
    private val binding get() = _binding!!
    private val firebaseStorage by lazy { FirebaseStorage.getInstance().getReference("images") }
    private val fireStore by lazy { FirebaseFirestore.getInstance() }
    private val list = listOf("Mumtoz Adabiyoti", "O'zbek Adabiyoti", "Jahon Adabiyoti")
    private lateinit var autoCompleteList: String
    private lateinit var photoUri: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddAdibBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        intiViews()
    }

    private fun intiViews() {
        binding.btnPhoto.setOnClickListener {
            photoLauncher.launch("image/*")
        }
        autoComplete()
        binding.btnSave.setOnClickListener {
            val fullName = binding.nameUN.text.toString().trim()
            val birthDay = binding.wasBorn.text.toString().trim()
            val deadDay = binding.dateDead.text.toString().trim()
            val fullAbout = binding.aboutAdib.text.toString().trim()
            if (fullAbout.isNotBlank() && ::photoUri.isInitialized && ::autoCompleteList.isInitialized) {
                uploadAdibTogo(fullName, birthDay, deadDay, fullAbout)
            } else {
                toast("Enter a data")
            }
        }
    }

    private fun uploadAdibTogo(fullName: String, birthDay: String, deadDay: String, full: String) {
        fireStore.collection("${autoCompleteList.subSequence(0, 6)}")
            .add(AdibTogo(fullName, birthDay, deadDay, autoCompleteList, full, photoUri))
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    toast("Successfully saved")
                    findNavController().popBackStack()
                } else {
                    toast(it.exception?.message.toString())
                }
            }
    }

    private val photoLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri ?: return@registerForActivityResult
            val random = UUID.randomUUID().toString()
            binding.imageView.setImageURI(uri)
            firebaseStorage.child("image$random").putFile(uri).addOnSuccessListener {
                firebaseStorage.downloadUrl.addOnSuccessListener {
                    photoUri = it.toString()
                }
            }
        }

    private fun autoComplete() {
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, list)
        binding.autoComplete.setAdapter(adapter)
        binding.autoComplete.setOnItemClickListener { adapterView, view, pos, l ->
            autoCompleteList = list[pos]
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}