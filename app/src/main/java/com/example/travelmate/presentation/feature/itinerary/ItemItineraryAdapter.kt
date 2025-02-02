package com.example.travelmate.presentation.feature.itinerary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import com.example.travelmate.databinding.ItemItineraryBinding
import com.example.travelmate.domain.model.destination.Itinerary

class ItemItineraryAdapter(val clickItemListener: (Itinerary) -> Unit) :
    ListAdapter<Itinerary, ItemItineraryAdapter.ItemItineraryViewHolder>(DIFF_UTIL) {

    inner class ItemItineraryViewHolder(private val binding: ItemItineraryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                clickItemListener(getItem(adapterPosition))
            }
        }

        fun bind(itinerary: Itinerary) {
            with(binding) {
                tvName.text = itinerary.name
                imageViewPhotoUrl.load(itinerary.photoUrl)
                tvDate.text = itinerary.date
                tvLocation.text = itinerary.location
                tvNote.text = itinerary.notes
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemItineraryViewHolder {
        return ItemItineraryViewHolder(
            ItemItineraryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemItineraryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Itinerary>() {
            override fun areItemsTheSame(
                oldItem: Itinerary,
                newItem: Itinerary,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Itinerary,
                newItem: Itinerary,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
