package com.ikurek.scandroid.features.search.ui

import com.ikurek.scandroid.core.design.patterns.savedscans.SavedScanListItem
import com.ikurek.scandroid.core.design.patterns.savedscans.SavedScanListItem.SavedScanListItemFiles
import com.ikurek.scandroid.features.savedscans.data.model.SavedScan
import com.ikurek.scandroid.features.savedscans.data.model.SavedScanFiles
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import javax.inject.Inject

internal class SearchListBuilder @Inject constructor() {

    fun build(scans: List<SavedScan>) = scans.map(::toListItem)

    private fun toListItem(savedScan: SavedScan) = SavedScanListItem(
        id = savedScan.id,
        name = savedScan.name,
        createdAt = savedScan.createdAt.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)),
        files = when (savedScan.files) {
            is SavedScanFiles.ImagesOnly -> SavedScanListItemFiles.ImagesOnly(
                imageCount = savedScan.files.imageFiles!!.size
            )

            is SavedScanFiles.PdfOnly -> SavedScanListItemFiles.PdfOnly
            is SavedScanFiles.PdfAndImages -> SavedScanListItemFiles.PdfAndImages(
                imageCount = savedScan.files.imageFiles!!.size
            )
        }
    )
}
