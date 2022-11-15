package com.example.adibtogo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adibtogo.databinding.ItemLayoutBinding
import com.example.adibtogo.model.AdibTogo

class AdibTogoAdapter : ListAdapter<AdibTogo, AdibTogoAdapter.AdibTogoViewHolder>(DiffCallBack()) {
    lateinit var onClick: (AdibTogo) -> Unit

    private class DiffCallBack : DiffUtil.ItemCallback<AdibTogo>() {
        override fun areItemsTheSame(oldItem: AdibTogo, newItem: AdibTogo): Boolean {
            return oldItem.fullName == newItem.fullName
        }

        override fun areContentsTheSame(oldItem: AdibTogo, newItem: AdibTogo): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdibTogoViewHolder {
        return AdibTogoViewHolder(
            ItemLayoutBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: AdibTogoViewHolder, position: Int) {
        holder.find(getItem(position))
    }

    inner class AdibTogoViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun find(adib: AdibTogo) {
            binding.apply {
                Glide.with(imageView)
                    .load(adib.image)
                    .into(imageView)

                textName.text = adib.fullName
                textDead.text = adib.deathYear
            }
            itemView.setOnClickListener {
                onClick(adib)
            }
        }
    }
}