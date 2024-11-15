package com.khrlanamm.eventlist.data.response

data class Event(
	val error: Boolean,
	val message: String,
	val listEvents: List<EventDetail>
)
