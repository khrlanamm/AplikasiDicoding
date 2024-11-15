package com.khrlanamm.eventlist.ui.finished

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.khrlanamm.eventlist.databinding.FragmentFinishedBinding
import com.khrlanamm.eventlist.ui.adapter.EventAdapter
import com.khrlanamm.eventlist.ui.detail.EventDetailActivity

class FinishedFragment : Fragment() {

    private var _binding: FragmentFinishedBinding? = null
    private val binding get() = _binding!!
    private val FinishedViewModel: FinishedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFinishedBinding.inflate(inflater, container, false)
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
        binding.pastEventsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.pastEventsRecyclerView.adapter = adapter

        FinishedViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        // Observe past events from ViewModel
        FinishedViewModel.pastEvents.observe(viewLifecycleOwner) { events ->
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
