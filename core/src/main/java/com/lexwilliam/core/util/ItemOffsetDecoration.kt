package com.lexwilliam.core.util

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.annotation.NonNull
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class ItemOffsetDecoration(
    private val spacing: Int,
    private val includeEdge: Boolean,
    private val headerRowCount: Int = 0
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val columnCount = getColumnCount(parent)
        val position = parent.getChildAdapterPosition(view) - headerRowCount * columnCount
        if (position >= 0) {
            val rowCount = getRowCount(parent, columnCount)
            val orientation = getOrientation(parent)
            val columnIndex = position % columnCount
            val rowIndex = position / columnCount
            if (includeEdge) {
                when (orientation) {
                    RecyclerView.VERTICAL -> {
                        outRect.left = getStartOffsetWithEdge(spacing, columnIndex, columnCount)
                        outRect.right = getEndOffsetWithEdge(spacing, columnIndex, columnCount)
                        outRect.top = getStartOffsetWithEdge(spacing, rowIndex, rowCount)
                        outRect.bottom = getEndOffsetWithEdge(spacing, rowIndex, rowCount)
                    }
                    RecyclerView.HORIZONTAL -> {
                        outRect.top = getStartOffsetWithEdge(spacing, columnIndex, columnCount)
                        outRect.bottom = getEndOffsetWithEdge(spacing, columnIndex, columnCount)
                        outRect.left = getStartOffsetWithEdge(spacing, rowIndex, rowCount)
                        outRect.right = getEndOffsetWithEdge(spacing, rowIndex, rowCount)
                    }
                }
            } else {
                when (orientation) {
                    RecyclerView.VERTICAL -> {
                        outRect.left = getStartOffsetWithoutEdge(spacing, columnIndex, columnCount)
                        outRect.right = getEndOffsetWithoutEdge(spacing, columnIndex, columnCount)
                        outRect.top = getStartOffsetWithoutEdge(spacing, rowIndex, rowCount)
                        outRect.bottom = getEndOffsetWithoutEdge(spacing, rowIndex, rowCount)
                    }
                    RecyclerView.HORIZONTAL -> {
                        outRect.top = getStartOffsetWithoutEdge(spacing, columnIndex, columnCount)
                        outRect.bottom = getEndOffsetWithoutEdge(spacing, columnIndex, columnCount)
                        outRect.left = getStartOffsetWithoutEdge(spacing, rowIndex, rowCount)
                        outRect.right = getEndOffsetWithoutEdge(spacing, rowIndex, rowCount)
                    }
                }
            }
        } else {
            outRect.left = 0
            outRect.right = 0
            outRect.top = 0
            outRect.bottom = 0
        }
    }

    private fun getColumnCount(parent: RecyclerView) = when (val layoutManager = parent.layoutManager) {
        is GridLayoutManager -> layoutManager.spanCount
        is StaggeredGridLayoutManager -> layoutManager.spanCount
        else -> 1
    }

    private fun getRowCount(parent: RecyclerView, columnCount: Int) =
        parent.adapter?.itemCount?.div(columnCount)?.minus(headerRowCount)

    private fun getOrientation(parent: RecyclerView) = when (val layoutManager = parent.layoutManager) {
        is LinearLayoutManager -> layoutManager.orientation
        is GridLayoutManager -> layoutManager.orientation
        is StaggeredGridLayoutManager -> layoutManager.orientation
        else -> RecyclerView.VERTICAL
    }

    private fun getStartOffsetWithEdge(spacing: Int, columnIndex: Int, columnCount: Int?): Int {
        if (columnCount == null) return spacing
        return spacing - spacing * columnIndex / columnCount
    }

    private fun getEndOffsetWithEdge(spacing: Int, columnIndex: Int, columnCount: Int?): Int {
        if (columnCount == null) return 0
        return spacing * (columnIndex + 1) / columnCount
    }

    private fun getStartOffsetWithoutEdge(spacing: Int, columnIndex: Int, columnCount: Int?): Int {
        if (columnCount == null) return 0
        return spacing * columnIndex / columnCount
    }

    private fun getEndOffsetWithoutEdge(spacing: Int, columnIndex: Int, columnCount: Int?): Int {
        if (columnCount == null) return spacing
        return spacing - spacing * (columnIndex + 1) / columnCount
    }

}