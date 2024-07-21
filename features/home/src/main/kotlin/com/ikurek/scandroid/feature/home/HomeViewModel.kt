package com.ikurek.scandroid.feature.home

import androidx.lifecycle.ViewModel
import com.ikurek.scandroid.analytics.ScreenTracker
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val screenTracker: ScreenTracker
) : ViewModel() {

    fun onDestinationChanged(route: String) {
        screenTracker.trackScreenView(route)
    }
}
