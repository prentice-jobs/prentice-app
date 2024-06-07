package com.prenticedev.prenticeapp.ui.customview

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton

class UploadOfferButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int =0
) : AppCompatButton(context, attrs,defStyleAttr) {

    private var isAttached: Boolean =true
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

    }


}