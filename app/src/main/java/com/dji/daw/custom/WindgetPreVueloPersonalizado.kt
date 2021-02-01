package com.dji.daw.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import dji.ux.widget.PreFlightStatusWidget

/**
 * Sobre escribir el metodo prevuelo
 */
class WindgetPreVueloPersonalizado  //endregion
//region Constructors
@JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyle: Int = 0) : PreFlightStatusWidget(context, attrs, defStyle) {
    //region Properties
    private var borderPaint: Paint? = null
    private var indicatorPaint: Paint? = null
    private var widthVar = 0
    private var heightVar = 0
    //endregion
    //region Override methods
    /** Personalizar el widget  */
    override fun initView(context: Context, attrs: AttributeSet, defStyleAttr: Int) {
        borderPaint = Paint()
        borderPaint!!.style = Paint.Style.STROKE
        borderPaint!!.color = Color.BLACK
        borderPaint!!.alpha = 255
        borderPaint!!.strokeWidth = 5f
        borderPaint!!.isAntiAlias = true
        indicatorPaint = Paint()
        indicatorPaint!!.style = Paint.Style.FILL
        indicatorPaint!!.color = Color.RED
        indicatorPaint!!.alpha = 255
        indicatorPaint!!.isAntiAlias = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        widthVar = MeasureSpec.getSize(widthMeasureSpec) / 2
        heightVar = MeasureSpec.getSize(heightMeasureSpec) / 2
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawCircle(widthVar.toFloat(), heightVar.toFloat(), (widthVar - STROKE_WIDTH).toFloat(), indicatorPaint!!)
        canvas.drawCircle(widthVar.toFloat(), heightVar.toFloat(), (widthVar - STROKE_WIDTH).toFloat(), borderPaint!!)
    }

    /** LLamar cuando la conexion cambie con la aeronave  */
    override fun onStatusChange(status: String, type: StatusType, blink: Boolean) {
        if (type != StatusType.OFFLINE) {
            indicatorPaint!!.color = Color.GREEN
        } else {
            indicatorPaint!!.color = Color.RED
        }
    } //endregion

    companion object {
        private const val STROKE_WIDTH = 5
    }
}