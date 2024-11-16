package com.khrlanamm.eventlist.data.remote.response

import com.khrlanamm.eventlist.data.local.entity.EventDetail

data class Event(
	val error: Boolean,
	val message: String,
	val listEvents: List<EventDetail>
)
