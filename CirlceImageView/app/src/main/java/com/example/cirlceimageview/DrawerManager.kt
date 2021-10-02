package com.example.cirlceimageview

import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import com.example.cirlceimageview.model.Badge

/**
 * Badge drawer manager
 * @author Ivan V on 21.02.2018.
 * @version 1.0
 */
class DrawerManager(view: View, attrs: AttributeSet) {
    private var attrController: AttributeController? = null
    private var drawer: BadgeDrawer? = null
    private fun initManager(view: View, attrs: AttributeSet) {
        attrController = AttributeController(view, attrs)
        drawer = BadgeDrawer(view, attrController!!.badge)
    }

    /**
     * Method to provide the badge drawer
     * @param canvas [Canvas] to drawing on [AppCompatImageView]
     */
    fun drawBadge(canvas: Canvas) {
        drawer!!.draw(canvas)
    }

    /**
     * Provide badge entity after attrs init
     * @return badge entity
     */
    val badge: Badge
        get() = attrController!!.badge

    init {
        initManager(view, attrs)
    }
}