package com.khrlanamm.eventlist.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.khrlanamm.eventlist.data.response.EventDetail
import com.khrlanamm.eventlist.databinding.ItemEventBinding

class EventAdapter(private val onEventClick: (EventDetail) -> Unit) :
    RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    private val events = mutableListOf<EventDetail>()

    fun submitList(eventList: List<EventDetail>) {
        events.clear()
        events.addAll(eventList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.bind(event)
        holder.itemView.setOnClickListener {
            onEventClick(event)
        }
    }

    override fun getItemCount(): Int = events.size

    class EventViewHolder(private val binding: ItemEventBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(event: EventDetail) {
            binding.eventName.text = event.name
            binding.eventSummary.text = event.summary
            Glide.with(binding.root.context)
                .load(event.imageLogo)
                .into(binding.eventImage)
        }
    }
}