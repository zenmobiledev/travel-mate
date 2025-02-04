package com.example.travelmate.presentation.feature.travel.view.fragment

import android.content.Intent
import android.os.Bundle
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
import com.example.travelmate.presentation.feature.travel.adapter.ItemDestinationAdapter
import com.example.travelmate.presentation.feature.travel.view.activity.DetailActivity
import com.example.travelmate.presentation.feature.travel.viewmodel.TravelViewModel
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

        binding.swipeRefresh.setOnRefreshListener {
            lifecycleScope.launch {
                val token = travelViewModel.getToken()
                if (!token.isNullOrEmpty()) {
                    travelViewModel.fetchDestinations(token = token)
                }
                binding.swipeRefresh.isRefreshing = false
            }
        }
    }

    private fun setupSearchListener(searchView: SearchView) {
        searchView.queryHint = "Search destination"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(query: String?): Boolean = false

            override fun onQueryTextSubmit(query: String?): Boolean {
                lifecycleScope.launch {
                    val token = travelViewModel.getToken()
                    if (token != null) {
                        travelViewModel.fetchDestinations(token = token, search = query)
                    }
                }
                return true
            }
        })
    }

    private fun setupRecyclerView() {
        binding.rvDestination.adapter = destinationAdapter
        binding.rvDestination.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    travelViewModel.isLoading.collect {
                        binding.shimmerDestination.isVisible = it
                        binding.rvDestination.isVisible = !it
                    }
                }

                launch {
                    travelViewModel.errorMessage.filterNotNull().collect {
                        Toast.makeText(view?.context, "Error: $it", Toast.LENGTH_LONG).show()
                        binding.swipeRefresh.isRefreshing = false
                    }
                }

                launch {
                    travelViewModel.destinationData.collect {
                        destinationAdapter.submitList(it.destinations)
                    }
                }

                launch {
                    val token = travelViewModel.getToken()
                    if (!token.isNullOrEmpty() && travelViewModel.destinationData.value.destinations.isEmpty()) {
                        travelViewModel.fetchDestinations(token = token)
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