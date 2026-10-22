# Dicoding Events

Android app that lists Dicoding events from the Dicoding Event API. Users browse upcoming and finished events, search by name, open an event for its details and registration link, save favorites, and switch between light and dark theme.

Built in Kotlin as the final submission of the Fundamental Android class at Dicoding, during the Mobile Development cohort of Bangkit Academy 2024.

## Features

- Home, Upcoming, and Finished tabs with bottom navigation
- Event search
- Event detail with organizer, schedule, remaining quota, description, and a button that opens the event page
- Favorite events saved locally with Room
- Light and dark theme, saved with DataStore
- Splash screen

## Tech stack

Kotlin, XML layouts, Navigation component, ViewModel and LiveData, coroutines, Retrofit with Gson, Room, DataStore Preferences, Glide.

## Project structure

```
app/src/main/java/com/khrlanamm/eventlist/
  data/            favorites repository, settings, Room database, Retrofit service
  ui/home/         home tab and theme setting
  ui/upcoming/     upcoming events
  ui/finished/     finished events
  ui/detail/       event detail
  ui/favorites/    saved events
```

## Run locally

Open the project in Android Studio and run the `app` configuration on a device or emulator with Android 7.0 (API 24) or newer. The app reads events from `https://event-api.dicoding.dev/` and needs no API key.
