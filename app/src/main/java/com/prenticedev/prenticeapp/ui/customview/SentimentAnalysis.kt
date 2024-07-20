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
        alpha = 255
        textAlign = Paint.Align.CENTER
    }
    private val grayColor = Paint().apply {
        textSize = 30f
        color = Color.GRAY
        alpha = 255
        textAlign = Paint.Align.CENTER
    }
    private val greenColor = Paint().apply {
        textSize = 30f
        color = Color.GREEN
        alpha = 255
        textAlign = Paint.Align.CENTER
    }

    private val redColorLighter = Paint(redColor).apply { alpha = 100 }
    private val grayColorLighter = Paint(grayColor).apply { alpha = 100 }
    private val greenColorLighter = Paint(greenColor).apply { alpha = 100 }

    private var redPercentage = 0
    private var greyPercentage = 0
    private var greenPercentage = 0

    fun setPercentage(red: Int, grey: Int, green: Int) {
        redPercentage = red
        greyPercentage = grey
        greenPercentage = green
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val width = width.toFloat()
        val height = height.toFloat()

        val totalPercentage = redPercentage + greyPercentage + greenPercentage
        val redWidth = width * (redPercentage / totalPercentage.toFloat())
        val greyWidth = width * (greyPercentage / totalPercentage.toFloat())
        val greenWidth = width * (greenPercentage / totalPercentage.toFloat())


        canvas.drawRect(0f, 0f, greenWidth, height, greenColorLighter)

        canvas.drawRect(greenWidth, 0f, greenWidth + greyWidth, height, grayColorLighter)

        canvas.drawRect(greenWidth + greyWidth, 0f, width, height, redColorLighter)


        val textY = height / 2f - (redColor.descent() + redColor.ascent()) / 2 // Center vertically


        val greenTextX = greenWidth / 2f
        canvas.drawText("$greenPercentage%", greenTextX, textY, greenColor)


        val greyTextX = greenWidth + greyWidth / 2f
        canvas.drawText("$greyPercentage%", greyTextX, textY, grayColor)


        val redTextX = greenWidth + greyWidth + redWidth / 2f
        canvas.drawText("$redPercentage%", redTextX, textY, redColor)
    }
}