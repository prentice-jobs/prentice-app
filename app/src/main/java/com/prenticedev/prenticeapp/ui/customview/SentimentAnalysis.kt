package com.prenticedev.prenticeapp.ui.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class SentimentAnalysis @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    View(context, attrs, defStyleAttr) {
    private val redColor = Paint().apply {
        textSize = 30f
        color = Color.RED
    }
    private val grayColor = Paint().apply {
        textSize = 30f
        color = Color.GRAY
    }
    private val greenColor = Paint().apply {
        textSize = 30f
        color = Color.GREEN
    }

    private var redPercentage = 0
    private var greyPercentage = 0
    private var greenPercentage = 0

    fun setPercentage(red: Int, grey: Int, green: Int) {
        redPercentage = red
        greyPercentage = grey
        greenPercentage = green
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val width = width
        val height = height

        canvas.drawRect(0f, 0f, width / 3f, height.toFloat(), redColor)
        canvas.drawRect(width / 3f, 0f, 2 * width / 3f, height.toFloat(), grayColor)
        canvas.drawRect(2 * width / 3f, 0f, width.toFloat(), height.toFloat(), greenColor)
    }
}