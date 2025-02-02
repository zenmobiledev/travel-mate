package com.example.travelmate.presentation.feature.travel

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelmate.R
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
            val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                putExtra(DetailActivity.EXTRA_DESTINATION, it)
            }
            startActivity(intent)
        }
    }
    private lateinit var travelViewModel: TravelViewModel

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

        // The menu is displayed on the toolbar
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.top_bar_menu, menu)

                // Set up the search icon
                val searchItem = menu.findItem(R.id.menu_search)
                val searchView =
                    searchItem?.actionView as SearchView
                setupSearchListener(searchView)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean = false
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        setupRecyclerView()
        setupObserver()
    }

    private fun setupSearchListener(searchView: SearchView) {
        searchView.queryHint = "Search destination"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(query: String?): Boolean = true

            override fun onQueryTextSubmit(query: String?): Boolean {
                return if (query != null) {
                    viewLifecycleOwner.lifecycleScope.launch {
                        // submit data to the adapter
                        Toast.makeText(requireContext(), "Search: $query", Toast.LENGTH_SHORT)
                            .show()
                    }
                    true
                } else {
                    false
                }
            }
        })
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
                    if (!token.isNullOrEmpty() && travelViewModel.getAllDestination.value.isNullOrEmpty()) {
                        travelViewModel.fetchDestinations(page = 1, token = token)
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