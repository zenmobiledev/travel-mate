package com.example.travelmate.presentation.feature.travel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import com.example.travelmate.databinding.ItemDestinationBinding
import com.example.travelmate.domain.model.destination.DestinationUser
import java.util.Locale

class ItemDestinationAdapter(val clickItemListener: (DestinationUser.Destination) -> Unit) :
    ListAdapter<DestinationUser.Destination, ItemDestinationAdapter.DestinationViewHolder>(DIFF_UTIL) {
    inner class DestinationViewHolder(private val binding: ItemDestinationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                clickItemListener(getItem(adapterPosition))
            }
        }

        fun bind(destination: DestinationUser.Destination) {
            with(binding) {
                imageViewPhotoUrl.load(destination.photoUrl)
                tvCategory.text = destination.category
                tvName.text = destination.name
                tvLocation.text = destination.location
                tvRating.text = String.format(Locale.US, "%.1f", destination.rating)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DestinationViewHolder {
        return DestinationViewHolder(
            ItemDestinationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DestinationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<DestinationUser.Destination>() {
            override fun areItemsTheSame(
                oldItem: DestinationUser.Destination,
                newItem: DestinationUser.Destination,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: DestinationUser.Destination,
                newItem: DestinationUser.Destination,
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}