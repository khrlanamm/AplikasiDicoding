package com.khrlanamm.eventlist.ui.favorites

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.khrlanamm.eventlist.databinding.ActivityFavoritesBinding
import com.khrlanamm.eventlist.ui.detail.EventDetailActivity
import com.khrlanamm.eventlist.ui.adapter.EventAdapter

class FavoritesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoritesBinding
    private val viewModel: FavoritesViewModel by viewModels<FavoritesViewModel> {
        FavoritesViewModel.getInstance(
            this
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.setNavigationOnClickListener { finish() }

        // Setup RecyclerView with onEventClick callback
        val adapter = EventAdapter { event ->
            val iDetail = Intent(this@FavoritesActivity, EventDetailActivity::class.java)
            iDetail.putExtra(EventDetailActivity.EXTRA_EVENT, event)
            startActivity(iDetail)
        }
        binding.favoritesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.favoritesRecyclerView.adapter = adapter

        // Observe past events from ViewModel
        viewModel.getAllFavorites().observe(this) { events ->
            adapter.submitList(events)
        }
    }
}