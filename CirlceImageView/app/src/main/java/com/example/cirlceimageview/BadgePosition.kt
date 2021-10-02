package com.example.cirlceimageview

import android.view.View
import com.example.cirlceimageview.constant.Constants
import com.example.cirlceimageview.model.Badge

/**
 * Determine the position of a badge on view
 * @author Ivan V on 24.02.2018.
 * @version 1.0
 */
class BadgePosition internal constructor(private val view: View, private val badge: Badge) {
    private var isDrawBackgroundAdded = false
    private var pivotX = 0f
    private var pivotY = 0f
    private var viewHeight = 0f
    private var viewWidth = 0f
    var badgeWidth = 0f
        private set
    var badgeHeight = 0f
        private set
    var deltaX = 0f
        private set
    var deltaY = 0f
        private set

    private fun init() {
        isDrawBackgroundAdded = badge.backgroundDrawable != null
        pivotX = view.pivotX
        pivotY = view.pivotY
        viewHeight = view.measuredHeight.toFloat()
        viewWidth = view.measuredWidth.toFloat()
    }

    /**
     * Compute position of a badge on view
     */
    fun compute(): BadgePosition {
        when (badge.position) {
            TOP_LEFT -> computeTopLeft()
            TOP_RIGHT -> computeTopRight()
            BOTTOM_LEFT -> computeBottomLeft()
            BOTTOM_RIGHT -> computeBottomRight()
            CENTER -> computeCenter()
        }
        return this
    }

    private fun defineMeasurement() {
        if (checkFixedRadius()) {
            val maxMeasurement = Math.max(badgeWidth, badgeHeight)
            badgeWidth = maxMeasurement
            badgeHeight = maxMeasurement
        } else if (badge.isOvalAfterFirst && badge.value <= Constants.MAX_CIRCLE_NUMBER
                || badge.isRoundBadge && badgeWidth < badgeHeight) {
            badgeWidth = badgeHeight
        }
    }

    private fun computeTopLeft() {
        if (isDrawBackgroundAdded) {
            defineMeasurement()
            deltaX = pivotX - viewWidth / 2 + badgeWidth / 2
            deltaY = pivotY - viewHeight / 2 + badgeHeight / 2
        } else {
            deltaX = pivotX - viewWidth / 2 + badge.radius
            deltaY = pivotY - viewHeight / 2 + badge.radius
        }
    }

    private fun computeTopRight() {
        if (isDrawBackgroundAdded) {
            defineMeasurement()
            deltaX = pivotX + viewWidth / 2 - badgeWidth / 2
            deltaY = pivotY - viewHeight / 2 + badgeHeight / 2
        } else {
            deltaX = pivotX + viewWidth / 2 - badge.radius
            deltaY = pivotY - viewHeight / 2 + badge.radius
        }
    }

    private fun computeBottomLeft() {
        if (isDrawBackgroundAdded) {
            defineMeasurement()
            deltaX = pivotX - viewWidth / 2 + badgeWidth / 2
            deltaY = pivotY + viewHeight / 2 - badgeHeight / 2
        } else {
            deltaX = pivotX - viewWidth / 2 + badge.radius
            deltaY = pivotY + viewHeight / 2 - badge.radius
        }
    }

    private fun computeBottomRight() {
        if (isDrawBackgroundAdded) {
            defineMeasurement()
            deltaX = pivotX + viewWidth / 2 - badgeWidth / 2
            deltaY = pivotY + viewHeight / 2 - badgeHeight / 2
        } else {
            deltaX = pivotX + viewWidth / 2 - badge.radius
            deltaY = pivotY + viewHeight / 2 - badge.radius
        }
    }

    private fun computeCenter() {
        deltaX = viewWidth / 2
        deltaY = viewHeight / 2
        if (isDrawBackgroundAdded) {
            defineMeasurement()
        }
    }

    private fun computeBadgeWidth() {
        badgeWidth = if (badge.fixedRadiusSize != Constants.NO_INIT) {
            (badge.fixedRadiusSize * 2f)
        } else if (isDrawBackgroundAdded
                && badge.value > Constants.MAX_CIRCLE_NUMBER && badge.isOvalAfterFirst
                && !badge.isFixedRadius) {
            (badge.textWidth + badge.padding * 4f)
        } else {
            (badge.textWidth + badge.padding * 2f)
        }
    }

    private fun computeBadgeHeight() {
        badgeHeight = if (badge.fixedRadiusSize != Constants.NO_INIT) {
            (badge.fixedRadiusSize * 2f)
        } else {
            (badge.badgeTextSize + badge.padding * 2f)
        }
    }

    private fun computeRadius() {
        if (badge.fixedRadiusSize != Constants.NO_INIT) {
            badge.radius = badge.fixedRadiusSize
        } else {
            badge.radius = badgeWidth / 2f
        }
    }

    private fun checkFixedRadius(): Boolean {
        return badge.fixedRadiusSize != Constants.NO_INIT || badge.isFixedRadius
    }

    companion object {
        const val TOP_LEFT = 0
        const val TOP_RIGHT = 1
        const val BOTTOM_LEFT = 2
        const val BOTTOM_RIGHT = 3
        const val CENTER = 4
    }

    init {
        init()
        computeBadgeWidth()
        computeBadgeHeight()
        computeRadius()
    }
}