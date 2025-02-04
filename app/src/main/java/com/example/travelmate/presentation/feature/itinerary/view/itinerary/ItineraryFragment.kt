package com.example.travelmate.presentation.feature.itinerary.view.itinerary

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelmate.databinding.FragmentItineraryBinding
import com.example.travelmate.presentation.feature.itinerary.adapter.ItemItineraryAdapter
import com.example.travelmate.presentation.feature.itinerary.view.itinerarydetail.ItineraryDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ItineraryFragment : Fragment() {

    private var _binding: FragmentItineraryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val itineraryAdapter by lazy {
        ItemItineraryAdapter {
            val intent = Intent(requireContext(), ItineraryDetailActivity::class.java).apply {
                putExtra(ItineraryDetailActivity.EXTRA_ITINERARY, it)
            }
            startActivity(intent)
        }
    }

    private lateinit var itineraryViewModel: ItineraryViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        itineraryViewModel =
            ViewModelProvider(this)[ItineraryViewModel::class.java]
        _binding = FragmentItineraryBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObserver()
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    itineraryViewModel.itineraryList.collect {
                        if (it.isEmpty()) {
                            binding.tvNoData.isVisible = true
                            binding.rvItinerary.isVisible = false
                        } else {
                            binding.tvNoData.isVisible = false
                            binding.rvItinerary.isVisible = true
                            itineraryAdapter.submitList(it)
                        }
                        itineraryViewModel.getItinerary()
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        with(binding.rvItinerary) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = itineraryAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}