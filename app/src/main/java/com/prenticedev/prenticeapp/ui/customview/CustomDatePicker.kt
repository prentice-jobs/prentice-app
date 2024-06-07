package com.prenticedev.prenticeapp.ui.customview

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.prenticedev.prenticeapp.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CustomDatePicker @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    deffStyleAttr: Int = android.R.attr.editTextStyle
) : AppCompatEditText(context, attrs, deffStyleAttr) {
    private var cal = Calendar.getInstance()
    private var icon: Drawable? = null

    init {
        isFocusable = false
        setOnClickListener {
            showDatePicker()
            updateView()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        icon = ContextCompat.getDrawable(context, R.drawable.baseline_calendar_24)
        icon?.let {
            val right = width - paddingRight
            val left = width - it.intrinsicWidth
            val top = (height - it.intrinsicWidth) / 2
            val bottom = top + it.intrinsicWidth
            it.setBounds(left, top, right, bottom)
            it.draw(canvas)
        }
    }

    private fun showDatePicker() {
        val datePickerDialog = DatePickerDialog(
            context,
            { _, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateView()
            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    private fun updateView() {
        val calFormat = "MM/dd/yyyy"
        val sdf = SimpleDateFormat(calFormat, Locale.getDefault())
        setText(sdf.format(cal.time))
    }
}