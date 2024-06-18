package com.ikurek.scandroid.features.savedscans

import com.ikurek.scandroid.features.savedscans.data.model.SavedScan
import com.ikurek.scandroid.features.savedscans.data.model.SavedScanFiles
import com.ikurek.scandroid.features.savedscans.model.SavedScanListItem
import com.ikurek.scandroid.features.savedscans.model.SavedScanListItem.SavedScanListItemFiles
import com.ikurek.scandroid.features.savedscans.model.SortingMode
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import javax.inject.Inject

internal class SavedScansListBuilder @Inject constructor() {

    fun build(
        scans: List<SavedScan>,
        sortingMode: SortingMode
    ) = scans.sort(sortingMode).map(::toListItem)

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

    private fun List<SavedScan>.sort(sortingMode: SortingMode) = when (sortingMode) {
        SortingMode.Alphabetical -> sortedBy { it.name }
        SortingMode.Newest -> sortedByDescending { it.createdAt }
        SortingMode.Oldest -> sortedBy { it.createdAt }
        SortingMode.RecentlyViewed -> sortedBy { it.lastAccessedAt }
    }
}
