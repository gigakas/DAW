package com.dji.daw

import android.app.Activity
import android.graphics.Point
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.FrameLayout
import android.widget.RelativeLayout
import com.dji.daw.controles.ObjetoDensidadPX.dip2px
import dji.common.airlink.PhysicalSource
import dji.keysdk.CameraKey
import dji.keysdk.KeyManager
import dji.sdk.camera.VideoFeeder
import dji.ux.widget.FPVWidget
import dji.ux.widget.MapWidget
import dji.ux.widget.MapWidget.OnMapReadyListener
import dji.ux.widget.controls.CameraControlsWidget

/**
 * Acitivdad que muestra los widgets juntos
 */
class WidgetVueloFPV : Activity() {
    private var mapWidget: MapWidget? = null
    private var parentView: ViewGroup? = null
    private var fpvWidget: FPVWidget? = null
    private var secondaryFPVWidget: FPVWidget? = null
    private var primaryVideoView: RelativeLayout? = null
    private var secondaryVideoView: FrameLayout? = null
    private var isMapMini = true
    private var height = 0
    private var width = 0
    private var margin = 0
    private var deviceWidth = 0
    private var deviceHeight = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_default_widgets)
        height = dip2px(this, 100f)
        width = dip2px(this, 150f)
        margin = dip2px(this, 12f)
        val windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val outPoint = Point()
        display.getRealSize(outPoint)
        deviceHeight = outPoint.y
        deviceWidth = outPoint.x
        mapWidget = findViewById(R.id.map_widget)
        mapWidget?.initGoogleMap { map ->
            map.setOnMapClickListener { onViewClick(mapWidget) }
            map.uiSettings.setZoomControlsEnabled(false)
        }
        mapWidget?.onCreate(savedInstanceState)
        parentView = findViewById<View>(R.id.root_view) as ViewGroup
        fpvWidget = findViewById(R.id.fpv_widget)
        fpvWidget?.setOnClickListener { onViewClick(fpvWidget) }
        primaryVideoView = findViewById<View>(R.id.fpv_container) as RelativeLayout
        secondaryVideoView = findViewById<View>(R.id.secondary_video_view) as FrameLayout
        secondaryFPVWidget = findViewById(R.id.secondary_fpv_widget)
        secondaryFPVWidget?.setOnClickListener { intercambiarCamaras() }
        if (VideoFeeder.getInstance() != null) {
            //If secondary video feed is already initialized, get video source
            updateSecondaryVideoVisibility(VideoFeeder.getInstance().secondaryVideoFeed.videoSource != PhysicalSource.UNKNOWN)
            //If secondary video feed is not yet initialized, wait for active status
            VideoFeeder.getInstance().secondaryVideoFeed
                    .addVideoActiveStatusListener { isActive: Boolean -> runOnUiThread { updateSecondaryVideoVisibility(isActive) } }
        }
    }

    private fun onViewClick(view: View?) {
        if (view === fpvWidget && !isMapMini) {
            escalarWidgetFPV(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT, 0, 0)
            reorganizarFuentesCaptura()
            val mapViewAnimation = ResizeAnimation(mapWidget, deviceWidth, deviceHeight, width, height, margin)
            mapWidget!!.startAnimation(mapViewAnimation)
            isMapMini = true
        } else if (view === mapWidget && isMapMini) {
            escoderPaneles()
            escalarWidgetFPV(width, height, margin, 12)
            reorganizarFuentesCaptura()
            val mapViewAnimation = ResizeAnimation(mapWidget, width, height, deviceWidth, deviceHeight, 0)
            mapWidget!!.startAnimation(mapViewAnimation)
            isMapMini = false
        }
    }

    private fun escalarWidgetFPV(width: Int, height: Int, margin: Int, fpvInsertPosition: Int) {
        val fpvParams = primaryVideoView!!.layoutParams as RelativeLayout.LayoutParams
        fpvParams.height = height
        fpvParams.width = width
        fpvParams.rightMargin = margin
        fpvParams.bottomMargin = margin
        if (isMapMini) {
            fpvParams.addRule(RelativeLayout.CENTER_IN_PARENT, 0)
            fpvParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE)
            fpvParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
        } else {
            fpvParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0)
            fpvParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0)
            fpvParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE)
        }
        primaryVideoView!!.layoutParams = fpvParams
        parentView!!.removeView(primaryVideoView)
        parentView!!.addView(primaryVideoView, fpvInsertPosition)
    }

    private fun reorganizarFuentesCaptura() {
        val cameraCapturePanel = findViewById<View>(R.id.CameraCapturePanel)
        parentView!!.removeView(cameraCapturePanel)
        parentView!!.addView(cameraCapturePanel, if (isMapMini) 9 else 13)
    }

    private fun intercambiarCamaras() {
        if (secondaryFPVWidget!!.videoSource == FPVWidget.VideoSource.SECONDARY) {
            fpvWidget!!.videoSource = FPVWidget.VideoSource.SECONDARY
            secondaryFPVWidget!!.videoSource = FPVWidget.VideoSource.PRIMARY
        } else {
            fpvWidget!!.videoSource = FPVWidget.VideoSource.PRIMARY
            secondaryFPVWidget!!.videoSource = FPVWidget.VideoSource.SECONDARY
        }
    }

    private fun updateSecondaryVideoVisibility(isActive: Boolean) {
        if (isActive) {
            secondaryVideoView!!.visibility = View.VISIBLE
        } else {
            secondaryVideoView!!.visibility = View.GONE
        }
    }

    private fun escoderPaneles() {
        //Paneles que aparecen segun configuracion
        if (KeyManager.getInstance() != null) {
            KeyManager.getInstance().setValue(CameraKey.create(CameraKey.HISTOGRAM_ENABLED), false, null)
            KeyManager.getInstance().setValue(CameraKey.create(CameraKey.COLOR_WAVEFORM_ENABLED), false, null)
        }

        //These panels have buttons that toggle them, so call the methods to make sure the button state is correct.
        val controlsWidget: CameraControlsWidget = findViewById(R.id.CameraCapturePanel)
        controlsWidget.setAdvancedPanelVisibility(false)
        controlsWidget.setExposurePanelVisibility(false)

        //These panels don't have a button state, so we can just hide them.
        findViewById<View>(R.id.pre_flight_check_list).visibility = View.GONE
        findViewById<View>(R.id.rtk_panel).visibility = View.GONE
        findViewById<View>(R.id.spotlight_panel).visibility = View.GONE
        findViewById<View>(R.id.speaker_panel).visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        //Esconder menus de android
        val decorView = window.decorView
        decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        mapWidget!!.onResume()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        val runnable = Runnable { onResume() }
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(runnable, 300)
    }

    override fun onPause() {
        mapWidget!!.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mapWidget!!.onDestroy()
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapWidget!!.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapWidget!!.onLowMemory()
    }

    private inner class ResizeAnimation(private val mView: View?, private val mFromWidth: Int, private val mFromHeight: Int, private val mToWidth: Int, private val mToHeight: Int, private val mMargin: Int) : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
            val height = (mToHeight - mFromHeight) * interpolatedTime + mFromHeight
            val width = (mToWidth - mFromWidth) * interpolatedTime + mFromWidth
            val p = mView!!.layoutParams as RelativeLayout.LayoutParams
            p.height = height.toInt()
            p.width = width.toInt()
            p.rightMargin = mMargin
            p.bottomMargin = mMargin
            mView.requestLayout()
        }

        init {
            duration = 300
        }
    }
}