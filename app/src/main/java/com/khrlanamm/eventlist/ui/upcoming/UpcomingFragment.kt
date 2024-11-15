package com.khrlanamm.eventlist.ui.upcoming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import android.content.Intent
import androidx.core.view.isVisible
import com.khrlanamm.eventlist.databinding.FragmentUpcomingBinding
import com.khrlanamm.eventlist.ui.adapter.EventAdapter
import com.khrlanamm.eventlist.ui.detail.EventDetailActivity


class UpcomingFragment : Fragment() {

    private var _binding: FragmentUpcomingBinding? = null
    private val binding get() = _binding!!
    private val UpcomingViewModel: UpcomingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup RecyclerView with onEventClick callback
        val adapter = EventAdapter { event ->
            val iDetail = Intent(requireContext(), EventDetailActivity::class.java)
            iDetail.putExtra(EventDetailActivity.EXTRA_EVENT, event)
            startActivity(iDetail)
        }
        binding.upcomingEventsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.upcomingEventsRecyclerView.adapter = adapter

        UpcomingViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        // Observe past events from ViewModel
        UpcomingViewModel.upcomingEvents.observe(viewLifecycleOwner) { events ->
            adapter.submitList(events)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressbar.isVisible = isLoading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
