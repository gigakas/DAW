package com.dji.daw

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View.OnClickListener
import android.widget.*
import androidx.annotation.ColorInt
import androidx.core.graphics.ColorUtils
import com.dji.daw.R
import dji.common.flightcontroller.flyzone.FlyZoneCategory
import dji.ux.widget.MapWidget
import java.util.*

class ZonaVueloDialogoVista : ScrollView {
    private var all: CheckBox? = null
    private var auth: CheckBox? = null
    private var warning: CheckBox? = null
    private var enhancedWarning: CheckBox? = null
    private var restricted: CheckBox? = null
    private var btnCustomUnlockColor: Button? = null
    private var btnCustomUnlockSync: Button? = null

    constructor(context: Context?) : super(context) {
        inflate(context, R.layout.dialog_fly_zone, this)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        inflate(context, R.layout.dialog_fly_zone, this)
    }

    fun init(mapWidget: MapWidget) {
        initCheckboxes(mapWidget)
        initColors(mapWidget)
    }

    fun initCheckboxes(mapWidget: MapWidget) {
        all = findViewById(R.id.all)
        auth = findViewById(R.id.auth)
        warning = findViewById(R.id.warning)
        enhancedWarning = findViewById(R.id.enhanced_warning)
        restricted = findViewById(R.id.restricted)
        val switchCustomUnlock = findViewById<Switch>(R.id.custom_unlock_switch)
        switchCustomUnlock.isChecked = mapWidget.isCustomUnlockZonesVisible
        val listener: CompoundButton.OnCheckedChangeListener = object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(compoundButton: CompoundButton, isChecked: Boolean) {
                when (compoundButton.id) {
                    R.id.all -> {
                        auth?.setChecked(isChecked)
                        warning?.setChecked(isChecked)
                        enhancedWarning?.setChecked(isChecked)
                        restricted?.setChecked(isChecked)
                    }
                    R.id.custom_unlock_switch -> {
                        btnCustomUnlockColor!!.isEnabled = isChecked
                        btnCustomUnlockSync!!.isEnabled = isChecked
                        if (isChecked) {
                            mapWidget.showCustomUnlockZones(true)
                        } else {
                            mapWidget.showCustomUnlockZones(false)
                        }
                    }
                    else -> {
                        all?.setOnCheckedChangeListener(null)
                        all?.setChecked(auth?.isChecked() == true
                                && warning?.isChecked() == true
                                && enhancedWarning?.isChecked() == true
                                && restricted?.isChecked() == true)
                        all?.setOnCheckedChangeListener(this)
                    }
                }
            }
        }
        auth?.setChecked(mapWidget.isFlyZoneVisible(FlyZoneCategory.AUTHORIZATION))
        warning?.setChecked(mapWidget.isFlyZoneVisible(FlyZoneCategory.WARNING))
        enhancedWarning?.setChecked(mapWidget.isFlyZoneVisible(FlyZoneCategory.ENHANCED_WARNING))
        restricted?.setChecked(mapWidget.isFlyZoneVisible(FlyZoneCategory.RESTRICTED))
        all?.setChecked(auth?.isChecked == true
                && warning?.isChecked == true
                && enhancedWarning?.isChecked == true
                && restricted?.isChecked == true)
        all?.setOnCheckedChangeListener(listener)
        auth?.setOnCheckedChangeListener(listener)
        warning?.setOnCheckedChangeListener(listener)
        enhancedWarning?.setOnCheckedChangeListener(listener)
        restricted?.setOnCheckedChangeListener(listener)
        switchCustomUnlock.setOnCheckedChangeListener(listener)
    }

    fun initColors(mapWidget: MapWidget) {
        val authColor = findViewById<Button>(R.id.auth_color)
        val warningColor = findViewById<Button>(R.id.warning_color)
        val enhancedWarningColor = findViewById<Button>(R.id.enhanced_warning_color)
        val restrictedColor = findViewById<Button>(R.id.restricted_color)
        val maxHeightColor = findViewById<Button>(R.id.max_height_color)
        val selfUnlockColor = findViewById<Button>(R.id.self_unlock_color)
        btnCustomUnlockColor = findViewById(R.id.custom_unlock_color)
        btnCustomUnlockSync = findViewById(R.id.custom_unlock_sync)
        btnCustomUnlockColor?.isEnabled = mapWidget.isCustomUnlockZonesVisible
        btnCustomUnlockSync?.isEnabled = mapWidget.isCustomUnlockZonesVisible
        val STROKE_WIDTH = 15.0f
        authColor.background = getBackground(mapWidget.getFlyZoneColor(FlyZoneCategory.AUTHORIZATION),
                mapWidget.getFlyZoneAlpha(FlyZoneCategory.AUTHORIZATION),
                STROKE_WIDTH)
        warningColor.background = getBackground(mapWidget.getFlyZoneColor(FlyZoneCategory.WARNING),
                mapWidget.getFlyZoneAlpha(FlyZoneCategory.WARNING),
                STROKE_WIDTH)
        enhancedWarningColor.background = getBackground(mapWidget.getFlyZoneColor(FlyZoneCategory.ENHANCED_WARNING),
                mapWidget.getFlyZoneAlpha(FlyZoneCategory.ENHANCED_WARNING),
                STROKE_WIDTH)
        restrictedColor.background = getBackground(mapWidget.getFlyZoneColor(FlyZoneCategory.RESTRICTED),
                mapWidget.getFlyZoneAlpha(FlyZoneCategory.RESTRICTED),
                STROKE_WIDTH)
        maxHeightColor.background = getBackground(mapWidget.maximumHeightColor,
                mapWidget.maximumHeightAlpha, STROKE_WIDTH)
        selfUnlockColor.background = getBackground(mapWidget.selfUnlockColor,
                mapWidget.selfUnlockAlpha, STROKE_WIDTH)
        val onClickListener = OnClickListener { view ->
            val rnd = Random()
            @ColorInt var randomColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
            var alpha = 26
            val strokeWidth = mapWidget.flyZoneBorderWidth
            when (view.id) {
                R.id.auth_color -> {
                    alpha = mapWidget.getFlyZoneAlpha(FlyZoneCategory.AUTHORIZATION)
                    mapWidget.setFlyZoneColor(FlyZoneCategory.AUTHORIZATION, randomColor)
                }
                R.id.warning_color -> {
                    alpha = mapWidget.getFlyZoneAlpha(FlyZoneCategory.WARNING)
                    mapWidget.setFlyZoneColor(FlyZoneCategory.WARNING, randomColor)
                }
                R.id.enhanced_warning_color -> {
                    alpha = mapWidget.getFlyZoneAlpha(FlyZoneCategory.ENHANCED_WARNING)
                    mapWidget.setFlyZoneColor(FlyZoneCategory.ENHANCED_WARNING, randomColor)
                }
                R.id.restricted_color -> {
                    alpha = mapWidget.getFlyZoneAlpha(FlyZoneCategory.RESTRICTED)
                    mapWidget.setFlyZoneColor(FlyZoneCategory.RESTRICTED, randomColor)
                }
                R.id.max_height_color -> {
                    alpha = mapWidget.maximumHeightAlpha
                    mapWidget.maximumHeightColor = randomColor
                }
                R.id.self_unlock_color -> {
                    alpha = mapWidget.selfUnlockAlpha
                    mapWidget.selfUnlockColor = randomColor
                }
                R.id.custom_unlock_color -> {
                    mapWidget.customUnlockFlyZoneOverlayColor = randomColor
                    randomColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
                    mapWidget.customUnlockFlyZoneSentToAircraftOverlayColor = randomColor
                    randomColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
                    mapWidget.customUnlockFlyZoneEnabledOverlayColor = randomColor
                    return@OnClickListener
                }
                R.id.custom_unlock_sync -> {
                    mapWidget.syncCustomUnlockZones()
                    return@OnClickListener
                }
            }
            view.background = getBackground(randomColor, alpha, strokeWidth)
        }
        authColor.setOnClickListener(onClickListener)
        warningColor.setOnClickListener(onClickListener)
        enhancedWarningColor.setOnClickListener(onClickListener)
        restrictedColor.setOnClickListener(onClickListener)
        maxHeightColor.setOnClickListener(onClickListener)
        selfUnlockColor.setOnClickListener(onClickListener)
        btnCustomUnlockColor?.setOnClickListener(onClickListener)
        btnCustomUnlockSync?.setOnClickListener(onClickListener)
    }

    private fun getBackground(@ColorInt color: Int, alpha: Int, strokeWidth: Float): GradientDrawable {
        val drawable = GradientDrawable()
        drawable.shape = GradientDrawable.OVAL
        drawable.setStroke(strokeWidth.toInt(), color)
        drawable.setColor(ColorUtils.setAlphaComponent(color, alpha))
        return drawable
    }

    fun isFlyZoneEnabled(category: FlyZoneCategory?): Boolean {
        when (category) {
            FlyZoneCategory.AUTHORIZATION -> return auth!!.isChecked
            FlyZoneCategory.WARNING -> return warning!!.isChecked
            FlyZoneCategory.ENHANCED_WARNING -> return enhancedWarning!!.isChecked
            FlyZoneCategory.RESTRICTED -> return restricted!!.isChecked
        }
        return false
    }
}