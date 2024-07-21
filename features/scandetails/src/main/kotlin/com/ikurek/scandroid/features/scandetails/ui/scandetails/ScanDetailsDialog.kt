package com.ikurek.scandroid.features.scandetails.ui.scandetails

internal sealed interface ScanDetailsDialog {

    data object ShareFileTypeSelection : ScanDetailsDialog

    data object OpenFileTypeSelection : ScanDetailsDialog
}
