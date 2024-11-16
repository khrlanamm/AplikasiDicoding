package com.khrlanamm.eventlist.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.text.Html
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.khrlanamm.eventlist.databinding.ActivityEventDetailBinding
import com.khrlanamm.eventlist.data.local.entity.EventDetail
import com.khrlanamm.eventlist.R

class EventDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEventDetailBinding
    private val event: EventDetail? by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_EVENT, EventDetail::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_EVENT)
        }
    }

    private val viewModel by viewModels<EventDetailViewModel> {
        EventDetailViewModel.getInstance(
            this,
            event?.id ?: 0
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.setNavigationOnClickListener { finish() }

        event?.let { bindEventDetails(it) }
    }

    private fun bindEventDetails(event: EventDetail) {
        binding.eventName.text = event.name
        binding.eventOrganizer.text = "Penyelenggara: ${event.ownerName}"
        binding.eventTime.text = "Waktu Pelaksanaan: ${event.beginTime}"
        binding.eventQuota.text = "Kuota Pendaftaran: ${event.quota - event.registrants}"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            binding.eventDescription.text =
                Html.fromHtml(event.description, HtmlCompat.FROM_HTML_MODE_LEGACY)
        } else {
            binding.eventDescription.text = event.description
        }

        // Load event image using Glide
        Glide.with(this).load(event.imageLogo).into(binding.eventImage)

        // Handle button to open event link
        binding.eventLinkButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(event.link))
            startActivity(intent)
        }

        viewModel.favFromDatabase.observe(this@EventDetailActivity) { isFavorite ->
            binding.btnFav.apply {
                val iconRes = if (isFavorite) {
                    R.drawable.love
                } else {
                    R.drawable.love_border
                }
                setImageResource(iconRes)

                setOnClickListener {
                    if (isFavorite) {
                        viewModel.deleteEvent()
                        Toast.makeText(
                            this@EventDetailActivity,
                            "Event ini telah DIHAPUS dari daftar favorit anda!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        viewModel.insert(event)
                        Toast.makeText(
                            this@EventDetailActivity,
                            "Event ini berhasil DITAMBAHKAN ke daftar favorit anda!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                isVisible = true // Pastikan tombol terlihat
            }
        }
    }

    companion object {
        const val EXTRA_EVENT = "extra_event"
    }
}