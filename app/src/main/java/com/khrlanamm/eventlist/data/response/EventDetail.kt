package com.khrlanamm.eventlist.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EventDetail(
	val id: Int,
	val name: String,
	val summary: String,
	val ownerName: String,
	val beginTime: String,
	val description: String,
	val quota: Int,
	val registrants: Int,
	val imageLogo: String?,
	val link: String?
) : Parcelable