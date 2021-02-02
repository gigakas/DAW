package com.dji.daw.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.dji.daw.R
import dji.common.battery.ConnectionState
import dji.ux.model.base.BaseDynamicWidgetAppearances
import dji.ux.widget.BatteryWidget

/**
 *Sobre escribir el metodo bettery widget y personalizarlo
 */
class WidgetBateriaPersonalizado @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyle: Int = 0) : BatteryWidget(context, attrs, defStyle) {
    private var batteryValue: TextView? = null
    private var batteryIcon: ImageView? = null
    private var batteryIconRes = 0
    private var batteryIconErrorRes = 0

    /** Cambiar el layout del widget  */
    override fun initView(context: Context, attrs: AttributeSet, defStyle: Int) {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.customized_battery_widget, this)
        batteryValue = view.findViewById<View>(R.id.textview_battery_value) as TextView
        batteryIcon = view.findViewById<View>(R.id.imageview_battery_icon) as ImageView
        batteryValue!!.text = "0%"
        batteryIcon!!.setImageResource(R.mipmap.battery_error)
    }

    override fun getWidgetAppearances(): BaseDynamicWidgetAppearances? {
        return null
    }

    /** LLamar cada 5% de bateria consumido un alert  */
    override fun onBatteryPercentageChange(percentage: Int) {
        batteryValue!!.text = "$percentage%"
        if (percentage > 0 && percentage < 5) {
            batteryIconRes = R.mipmap.battery0
        } else if (percentage >= 5 && percentage < 15) {
            batteryIconRes = R.mipmap.battery1
        } else if (percentage >= 15 && percentage < 25) {
            batteryIconRes = R.mipmap.battery2
        } else if (percentage >= 25 && percentage < 35) {
            batteryIconRes = R.mipmap.battery3
        } else if (percentage >= 35 && percentage < 45) {
            batteryIconRes = R.mipmap.battery4
        } else if (percentage >= 45 && percentage < 55) {
            batteryIconRes = R.mipmap.battery5
        } else if (percentage >= 55 && percentage < 65) {
            batteryIconRes = R.mipmap.battery6
        } else if (percentage >= 65 && percentage < 75) {
            batteryIconRes = R.mipmap.battery7
        } else if (percentage >= 75 && percentage < 85) {
            batteryIconRes = R.mipmap.battery8
        } else if (percentage >= 85 && percentage < 95) {
            batteryIconRes = R.mipmap.battery9
        } else if (percentage >= 95 && percentage <= 100) {
            batteryIconRes = R.mipmap.battery10
        }
        updateBatteryIcon()
    }

    /** Llamar cuando el estatus de la bateria cambie  */
    override fun onBatteryConnectionStateChange(status: ConnectionState?) {
        batteryIconErrorRes = if (status != ConnectionState.NORMAL) {
            R.mipmap.battery_error
        } else {
            0
        }
        updateBatteryIcon()
    }

    private fun updateBatteryIcon() {
        if (batteryIconErrorRes != 0) {
            batteryIcon!!.setImageResource(batteryIconErrorRes)
        } else {
            batteryIcon!!.setImageResource(batteryIconRes)
        }
    }
}