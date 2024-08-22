package com.ikurek.scandroid.features.scandetails.ui.scandetails

import com.ikurek.scandroid.features.savedscans.data.model.ExtendedScanInfo

internal sealed interface ScanDetailsDialog {

    data object ShareFileTypeSelection : ScanDetailsDialog

    data object OpenFileTypeSelection : ScanDetailsDialog

    data object DeleteScanConfirmation : ScanDetailsDialog

    data class ExtendedScanInformation(val extendedScanInfo: ExtendedScanInfo) : ScanDetailsDialog
}
