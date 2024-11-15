package com.khrlanamm.eventlist.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.khrlanamm.eventlist.databinding.FragmentHomeBinding
import com.khrlanamm.eventlist.ui.detail.EventDetailActivity
import com.google.android.material.snackbar.Snackbar
import com.khrlanamm.eventlist.ui.adapter.EventAdapter
import com.khrlanamm.eventlist.R


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val switchTheme = binding.switchTheme

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        // Set up RecyclerView and Adapter
        val upcomingEventsAdapter = EventAdapter { event ->
            val iDetail = Intent(requireContext(), EventDetailActivity::class.java)
            iDetail.putExtra(EventDetailActivity.EXTRA_EVENT, event)
            startActivity(iDetail)
        }

        // Set up RecyclerView and Adapter
        val finishedEventsAdapter = EventAdapter { event ->
            val iDetail = Intent(requireContext(), EventDetailActivity::class.java)
            iDetail.putExtra(EventDetailActivity.EXTRA_EVENT, event)
            startActivity(iDetail)
        }

        // Set up RecyclerView and Adapter
        val searchedEventsAdapter = EventAdapter { event ->
            val iDetail = Intent(requireContext(), EventDetailActivity::class.java)
            iDetail.putExtra(EventDetailActivity.EXTRA_EVENT, event)
            startActivity(iDetail)
        }

        binding.upcomingEventsRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.upcomingEventsRecyclerView.adapter = upcomingEventsAdapter

        binding.finishedEventsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.finishedEventsRecyclerView.adapter = finishedEventsAdapter

        binding.searchEventsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.searchEventsRecyclerView.adapter = searchedEventsAdapter

        // Observe LiveData for upcoming events
        homeViewModel.upcomingEvents.observe(viewLifecycleOwner) { events ->
            upcomingEventsAdapter.submitList(events.take(5)) // Show max 5 events
        }

        // Observe LiveData for finished events
        homeViewModel.finishedEvents.observe(viewLifecycleOwner) { events ->
            finishedEventsAdapter.submitList(events.take(5)) // Show max 5 events
        }

        // Observe LiveData for searched events
        homeViewModel.searchedEvents.observe(viewLifecycleOwner) { events ->
            searchedEventsAdapter.submitList(events.take(40)) // Show max 40 events
        }

        // Loading indicator
        homeViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        // Error message with Snack-bar
        homeViewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            if (!message.isNullOrEmpty()) {
                Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
            }
        }

        // Set the query hint programmatically
        binding.searchView.queryHint = getString(R.string.search_event)

        // Set up SearchView listener
        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Start search on submit (optional if you only want to search on text change)
                query?.let { searchEvents(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Trigger search as the user types
                if (newText.isNullOrEmpty()) {
                    showHomeContent(true)  // Show home content if search field is empty
                } else {
                    searchEvents(newText)
                    showHomeContent(false)  // Show search results if there's text
                }
                return true  // Return true to indicate we're handling the change
            }
        })
    }

    private fun showHomeContent(isShowingHomeContent: Boolean) {
        binding.apply {
            searchEventsRecyclerView.isVisible = !isShowingHomeContent
            layoutHomeContent.isVisible = isShowingHomeContent
        }
    }

    // Search function to call ViewModel method
    private fun searchEvents(query: String) {
        homeViewModel.searchEvents(query)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
