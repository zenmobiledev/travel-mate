package com.example.travelmate.presentation.feature.travel

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelmate.databinding.FragmentTravelBinding
import com.example.travelmate.presentation.feature.travel.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TravelFragment : Fragment() {

    private var _binding: FragmentTravelBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val destinationAdapter by lazy {
        ItemDestinationAdapter {
            Toast.makeText(view?.context, "Click: ${it.name}", Toast.LENGTH_SHORT).show()
            val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                putExtra(DetailActivity.EXTRA_DESTINATION, it)
            }
            startActivity(intent)
        }
    }
    private lateinit var travelViewModel: TravelViewModel
    private val currentPage = 1
    private val limitPage = 10

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        travelViewModel = ViewModelProvider(this)[TravelViewModel::class.java]

        _binding = FragmentTravelBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObserver()
    }

    private fun setupRecyclerView() {
        binding.rvDestination.adapter = destinationAdapter
        binding.rvDestination.layoutManager = LinearLayoutManager(view?.context)
    }

    private fun setupObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    travelViewModel.isLoading.collect {
                        binding.shimmerDestination.isVisible = it
                    }
                }

                launch {
                    travelViewModel.errorMessage.filterNotNull().collect {
                        Toast.makeText(view?.context, "Error: $it", Toast.LENGTH_LONG).show()
                    }
                }

                launch {
                    travelViewModel.getAllDestination.collect {
                        destinationAdapter.submitList(it)
                    }
                }

                launch {
                    val token = travelViewModel.getToken()
                    Log.d("TravelFragment", "Get token: $token")
                    if (!token.isNullOrEmpty() && travelViewModel.getAllDestination.value.isNullOrEmpty()) {
                        travelViewModel.getAllDestination(token = token)
                    } else {
                        Log.e("TravelFragment", "Token is null or empty")
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}