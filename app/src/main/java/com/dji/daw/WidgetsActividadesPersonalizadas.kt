package com.dji.daw

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton
import com.dji.daw.R
import dji.ux.widget.FPVOverlayWidget
import dji.ux.widget.FPVWidget

class WidgetsActividadesPersonalizadas : Activity(), View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private var fpvWidget: FPVWidget? = null
    private var fpvOverlayWidget: FPVOverlayWidget? = null
    private var secondaryFpvWidget: FPVWidget? = null
    private var isOriginalSize = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customized_widgets)
        initView()
    }

    private fun initView() {
        fpvWidget = findViewById<View>(R.id.fpv_custom_widget) as FPVWidget
        fpvOverlayWidget = findViewById<View>(R.id.fpv_overlay_widget) as FPVOverlayWidget
        secondaryFpvWidget = findViewById<View>(R.id.secondary_fpv_custom_widget) as FPVWidget
        (findViewById<View>(R.id.checkbox_primary_camera_name) as CheckBox).setOnCheckedChangeListener(this)
        (findViewById<View>(R.id.checkbox_secondary_camera_name) as CheckBox).setOnCheckedChangeListener(this)
        (findViewById<View>(R.id.checkbox_primary_camera_side) as CheckBox).setOnCheckedChangeListener(this)
        (findViewById<View>(R.id.checkbox_secondary_camera_side) as CheckBox).setOnCheckedChangeListener(this)
        (findViewById<View>(R.id.checkbox_touch_focus) as CheckBox).setOnCheckedChangeListener(this)
        (findViewById<View>(R.id.checkbox_touch_metering) as CheckBox).setOnCheckedChangeListener(this)
        (findViewById<View>(R.id.checkbox_gimbal_control) as CheckBox).setOnCheckedChangeListener(this)
        (findViewById<View>(R.id.checkbox_display_grid) as CheckBox).setOnCheckedChangeListener(this)
        findViewById<View>(R.id.primary_video_feed).setOnClickListener(this)
        findViewById<View>(R.id.secondary_video_feed).setOnClickListener(this)
        findViewById<View>(R.id.auto_video_feed).setOnClickListener(this)
        findViewById<View>(R.id.change_size).setOnClickListener(this)
        findViewById<View>(R.id.grid_type_none).setOnClickListener(this)
        findViewById<View>(R.id.grid_type_parallel).setOnClickListener(this)
        findViewById<View>(R.id.grid_type_parallel_diagonal).setOnClickListener(this)
    }

    // On click event for button
    fun resizeView() {
        val params = fpvWidget!!.layoutParams
        if (!isOriginalSize) {
            params.height = 2 * fpvWidget!!.height
            params.width = 2 * fpvWidget!!.width
        } else {
            params.height = fpvWidget!!.height / 2
            params.width = fpvWidget!!.width / 2
        }
        isOriginalSize = !isOriginalSize
        fpvWidget!!.layoutParams = params
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.primary_video_feed -> {
                fpvWidget!!.videoSource = FPVWidget.VideoSource.PRIMARY
                secondaryFpvWidget!!.videoSource = FPVWidget.VideoSource.SECONDARY
            }
            R.id.secondary_video_feed -> {
                fpvWidget!!.videoSource = FPVWidget.VideoSource.SECONDARY
                secondaryFpvWidget!!.videoSource = FPVWidget.VideoSource.PRIMARY
            }
            R.id.auto_video_feed -> fpvWidget!!.videoSource = FPVWidget.VideoSource.AUTO
            R.id.change_size -> resizeView()
            R.id.grid_type_none -> fpvOverlayWidget!!.currentGridOverlayType = FPVOverlayWidget.GridOverlayType.NONE
            R.id.grid_type_parallel -> fpvOverlayWidget!!.currentGridOverlayType = FPVOverlayWidget.GridOverlayType.PARALLEL
            R.id.grid_type_parallel_diagonal -> fpvOverlayWidget!!.currentGridOverlayType = FPVOverlayWidget.GridOverlayType.PARALLEL_DIAGONAL
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        when (buttonView.id) {
            R.id.checkbox_primary_camera_name -> fpvWidget!!.setSourceCameraNameVisibility(isChecked)
            R.id.checkbox_secondary_camera_name -> secondaryFpvWidget!!.setSourceCameraNameVisibility(isChecked)
            R.id.checkbox_primary_camera_side -> fpvWidget!!.setSourceCameraSideVisibility(isChecked)
            R.id.checkbox_secondary_camera_side -> secondaryFpvWidget!!.setSourceCameraSideVisibility(isChecked)
            R.id.checkbox_touch_focus -> fpvOverlayWidget!!.isTouchFocusEnabled = isChecked
            R.id.checkbox_touch_metering -> fpvOverlayWidget!!.isSpotMeteringEnabled = isChecked
            R.id.checkbox_gimbal_control -> fpvOverlayWidget!!.isGimbalControlEnabled = isChecked
            R.id.checkbox_display_grid -> fpvOverlayWidget!!.isGridOverlayEnabled = isChecked
        }
    }
}