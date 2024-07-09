package com.ikurek.scandroid.features.settings.ui.about

import androidx.lifecycle.ViewModel
import com.ikurek.scandroid.features.settings.usecase.GetAppPackageIdentifier
import com.ikurek.scandroid.features.settings.usecase.GetAppVersion
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
internal class AboutViewModel @Inject constructor(
    private val getAppVersion: GetAppVersion,
    private val getAppPackageIdentifier: GetAppPackageIdentifier
) : ViewModel() {

    private val _state: MutableStateFlow<AboutState> = MutableStateFlow(
        AboutState(
            version = getAppVersion(),
            packageIdentifier = getAppPackageIdentifier()
        )
    )
    val state: StateFlow<AboutState> = _state
}
