package com.dji.daw

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.VectorDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi
import com.amap.api.maps.AMap
import com.amap.api.maps.model.HeatmapTileProvider
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.TileOverlay
import com.amap.api.maps.model.TileOverlayOptions
import com.dji.daw.R
import com.dji.mapkit.core.maps.DJIMap
import com.dji.mapkit.core.models.annotations.DJIMarker
import com.dji.mapkit.core.models.annotations.DJIMarkerOptions
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.GroundOverlay
import com.google.android.gms.maps.model.GroundOverlayOptions
import com.google.android.gms.maps.model.LatLngBounds
import com.here.android.mpa.common.GeoCoordinate
import com.here.android.mpa.common.MapSettings
import com.here.android.mpa.mapping.Map
import com.here.android.mpa.mapping.MapOverlay
import dji.common.flightcontroller.flyzone.FlyZoneCategory
import dji.ux.widget.MapWidget
import dji.ux.widget.MapWidget.OnMapReadyListener
import java.io.File
import java.util.*

class MapWidgetActivity : Activity(), CompoundButton.OnCheckedChangeListener, RadioGroup.OnCheckedChangeListener, View.OnClickListener, OnItemSelectedListener, SeekBar.OnSeekBarChangeListener {
    private var mapWidget: MapWidget? = null
    private var selectedIcon: ImageView? = null
    private val iconIds = intArrayOf(R.id.icon_1, R.id.icon_2, R.id.icon_3, R.id.icon_4, R.id.icon_5)
    private var iconSpinner: Spinner? = null
    private var lineSpinner: Spinner? = null
    private var lineWidthPicker: SeekBar? = null
    private var lineWidthValue = 0
    private var lineColor: TextView? = null
    private var mapOverlay: MapOverlay? = null
    private var groundOverlay: GroundOverlay? = null
    private var tileOverlay: TileOverlay? = null
    private var mapProvider = 0
    private var hereMap: Map? = null
    private var googleMap: GoogleMap? = null
    private var aMap: AMap? = null
    private var scrollView: ScrollView? = null
    private var btnPanel: ImageButton? = null
    private var isPanelOpen = true
    private var markerList: MutableList<DJIMarker>? = null
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_widget)
        mapWidget = findViewById(R.id.map_widget)
        markerList = ArrayList()
        val onMapReadyListener = OnMapReadyListener { map ->
            map.setMapType(DJIMap.MapType.NORMAL)
            map.setOnMarkerDragListener(object : DJIMap.OnMarkerDragListener {
                override fun onMarkerDragStart(djiMarker: DJIMarker) {
                    if ((markerList as ArrayList<DJIMarker>).contains(djiMarker)) {
                        Toast.makeText(this@MapWidgetActivity,
                                "Marker " + (markerList as ArrayList<DJIMarker>).indexOf(djiMarker) + " drag started",
                                Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onMarkerDrag(djiMarker: DJIMarker) {
                    // do nothing
                }

                override fun onMarkerDragEnd(djiMarker: DJIMarker) {
                    if (markerList?.contains(djiMarker) == true) {
                        Toast.makeText(this@MapWidgetActivity,
                                "Marker " + markerList?.indexOf(djiMarker) + " drag ended",
                                Toast.LENGTH_SHORT).show()
                    }
                }
            })
            mapWidget?.setOnMarkerClickListener(DJIMap.OnMarkerClickListener { djiMarker ->
                Toast.makeText(this@MapWidgetActivity, "Marker " + markerList?.indexOf(djiMarker) + " clicked",
                        Toast.LENGTH_SHORT).show()
                true
            })
            map.setOnMapClickListener { djiLatLng ->
                val marker = map.addMarker(DJIMarkerOptions().position(djiLatLng).draggable(true))
                (markerList as ArrayList<DJIMarker>).add(marker)
            }
        }
        val intent = intent
        mapProvider = intent.getIntExtra(MAP_PROVIDER, 0)
        when (mapProvider) {
            0 -> {
                val success = MapSettings.setIsolatedDiskCacheRootPath(
                        getExternalFilesDir(null).toString() + File.separator + ".here-maps-cache")
                if (success) {
                    mapWidget?.initHereMap(onMapReadyListener)
                }
            }
            1 -> mapWidget?.initGoogleMap(onMapReadyListener)
            2 -> mapWidget?.initAMap(onMapReadyListener)
            3 ->                 //TODO: Remove this key before putting on github
                mapWidget?.initMapboxMap(onMapReadyListener, resources.getString(R.string.mapbox_id))
            else -> mapWidget?.initMapboxMap(onMapReadyListener, resources.getString(R.string.mapbox_id))
        }
        mapWidget?.onCreate(savedInstanceState)
        findViewById<View>(R.id.btn_map_provider_test).setOnClickListener(this)
        (findViewById<View>(R.id.home_direction) as CheckBox).setOnCheckedChangeListener(this)
        (findViewById<View>(R.id.lock_bounds) as CheckBox).setOnCheckedChangeListener(this)
        (findViewById<View>(R.id.flight_path) as CheckBox).setOnCheckedChangeListener(this)
        (findViewById<View>(R.id.home_point) as CheckBox).setOnCheckedChangeListener(this)
        (findViewById<View>(R.id.gimbal_yaw) as CheckBox).setOnCheckedChangeListener(this)
        (findViewById<View>(R.id.flyzone_unlock) as CheckBox).setOnCheckedChangeListener(this)
        (findViewById<View>(R.id.flyzone_legend) as CheckBox).setOnCheckedChangeListener(this)
        (findViewById<View>(R.id.login_state_indicator) as CheckBox).setOnCheckedChangeListener(this)
        (findViewById<View>(R.id.map_center_selector) as RadioGroup).setOnCheckedChangeListener(this)
        scrollView = findViewById(R.id.settings_scroll_view)
        btnPanel = findViewById(R.id.btn_settings)
        btnPanel?.setOnClickListener(this)
        findViewById<View>(R.id.clear_flight_path).setOnClickListener(this)
        iconSpinner = findViewById(R.id.icon_spinner)
        iconSpinner?.setOnItemSelectedListener(this)
        val mapSpinner = findViewById<Spinner>(R.id.map_spinner)
        mapSpinner.setSelection(0, false) // so the listener won't be called before the map is initialized
        mapSpinner.onItemSelectedListener = this
        findViewById<View>(R.id.replace).setOnClickListener(this)
        selectedIcon = findViewById(R.id.icon_1)
        for (id in iconIds) {
            findViewById<View>(id).setOnClickListener(this)
        }
        findViewById<View>(R.id.icon_1).isSelected = true
        val flyZoneButton = findViewById<Button>(R.id.btn_fly_zone)
        mapWidget?.showAllFlyZones()
        flyZoneButton.setOnClickListener { showSelectFlyZoneDialog() }
        lineSpinner = findViewById(R.id.line_spinner)
        lineSpinner?.setOnItemSelectedListener(this)
        lineWidthPicker = findViewById(R.id.line_width_picker)
        lineWidthPicker?.setOnSeekBarChangeListener(this)
        lineColor = findViewById(R.id.line_color)
        lineColor?.setOnClickListener(this)
    }

    override fun onCheckedChanged(compoundButton: CompoundButton, isChecked: Boolean) {
        when (compoundButton.id) {
            R.id.home_direction -> mapWidget!!.isDirectionToHomeVisible = isChecked
            R.id.lock_bounds -> mapWidget!!.setAutoFrameMap(isChecked)
            R.id.flight_path -> mapWidget!!.isFlightPathVisible = isChecked
            R.id.home_point -> mapWidget!!.isHomeVisible = isChecked
            R.id.gimbal_yaw -> mapWidget!!.isGimbalAttitudeVisible = isChecked
            R.id.flyzone_unlock -> mapWidget!!.isTapToUnlockEnabled = isChecked
            R.id.flyzone_legend -> mapWidget!!.showFlyZoneLegend(isChecked)
            R.id.login_state_indicator -> mapWidget!!.showDJIAccountLoginIndicator(isChecked)
        }
    }

    override fun onCheckedChanged(radioGroup: RadioGroup, id: Int) {
        when (id) {
            R.id.map_center_aircraft -> mapWidget!!.setMapCenterLock(MapWidget.MapCenterLock.AIRCRAFT)
            R.id.map_center_home -> mapWidget!!.setMapCenterLock(MapWidget.MapCenterLock.HOME)
            R.id.map_center_none -> mapWidget!!.setMapCenterLock(MapWidget.MapCenterLock.NONE)
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onClick(view: View) {
        when (view.id) {
            R.id.clear_flight_path -> mapWidget!!.clearFlightPath()
            R.id.replace -> {
                val drawable = selectedIcon!!.drawable as VectorDrawable
                if ("Aeronave" == iconSpinner!!.selectedItem) {
                    mapWidget!!.aircraftBitmap = getBitmap(drawable)
                } else if ("Home" == iconSpinner!!.selectedItem) {
                    mapWidget!!.homeBitmap = getBitmap(drawable)
                } else if ("Gimbal Yaw" == iconSpinner!!.selectedItem) {
                    mapWidget!!.gimbalAttitudeBitmap = getBitmap(drawable)
                } else if ("Zonas bloqueadas" == iconSpinner!!.selectedItem) {
                    mapWidget!!.setSelfLockedBitmap(getBitmap(drawable), 0.5f, 0.5f)
                } else if ("Zonas no bloqueadas" == iconSpinner!!.selectedItem) {
                    mapWidget!!.setSelfUnlockedBitmap(getBitmap(drawable), 0.5f, 0.5f)
                }
            }
            R.id.icon_1, R.id.icon_2, R.id.icon_3, R.id.icon_4, R.id.icon_5 -> {
                val imageView = view as ImageView
                imageView.isSelected = true
                selectedIcon!!.isSelected = false
                selectedIcon = imageView
            }
            R.id.btn_map_provider_test -> addOverlay()
            R.id.line_color -> {
                setRandomLineColor()
                movePanel()
            }
            R.id.btn_settings -> movePanel()
        }
    }

    private fun movePanel() {
        val translationStart: Int
        val translationEnd: Int
        if (isPanelOpen) {
            translationStart = 0
            translationEnd = -scrollView!!.width
        } else {
            scrollView!!.bringToFront()
            translationStart = -scrollView!!.width
            translationEnd = 0
        }
        val animate = TranslateAnimation(
                translationStart.toFloat(), translationEnd.toFloat(), 0F, 0F)
        animate.duration = 300
        animate.fillAfter = true
        animate.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                if (isPanelOpen) {
                    mapWidget!!.bringToFront()
                }
                btnPanel!!.bringToFront()
                isPanelOpen = !isPanelOpen
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        scrollView!!.startAnimation(animate)
    }

    override fun onResume() {
        super.onResume()
        mapWidget!!.onResume()
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

    private fun showSelectFlyZoneDialog() {
        val flyZoneDialogView = ZonaVueloDialogoVista(this)
        mapWidget?.let { flyZoneDialogView.init(it) }
        val positiveClickListener = DialogInterface.OnClickListener { dialog, which ->
            mapWidget!!.setFlyZoneVisible(FlyZoneCategory.AUTHORIZATION,
                    flyZoneDialogView.isFlyZoneEnabled(FlyZoneCategory.AUTHORIZATION))
            mapWidget!!.setFlyZoneVisible(FlyZoneCategory.WARNING,
                    flyZoneDialogView.isFlyZoneEnabled(FlyZoneCategory.WARNING))
            mapWidget!!.setFlyZoneVisible(FlyZoneCategory.ENHANCED_WARNING,
                    flyZoneDialogView.isFlyZoneEnabled(FlyZoneCategory.ENHANCED_WARNING))
            mapWidget!!.setFlyZoneVisible(FlyZoneCategory.RESTRICTED,
                    flyZoneDialogView.isFlyZoneEnabled(FlyZoneCategory.RESTRICTED))
            dialog.dismiss()
        }
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Zonas de Vuelo")
        builder.setView(flyZoneDialogView)
        builder.setPositiveButton("OK", positiveClickListener)
        val dialog = builder.create()
        dialog.show()
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        when (parent.id) {
            R.id.map_spinner -> if (mapWidget!!.map != null) {
                when (id.toInt()) {
                    0 -> mapWidget!!.map.setMapType(DJIMap.MapType.NORMAL)
                    1 -> mapWidget!!.map.setMapType(DJIMap.MapType.SATELLITE)
                    else -> mapWidget!!.map.setMapType(DJIMap.MapType.HYBRID)
                }
            } else {
                Toast.makeText(applicationContext, R.string.error_map_not_initialized, Toast.LENGTH_SHORT).show()
            }
            R.id.line_spinner -> {
                var width = 5
                when (id.toInt()) {
                    0 -> {
                        width = mapWidget!!.directionToHomeWidth.toInt()
                        lineColor!!.visibility = View.VISIBLE
                        lineColor!!.setTextColor(mapWidget!!.directionToHomeColor)
                    }
                    1 -> {
                        width = mapWidget!!.flightPathWidth.toInt()
                        lineColor!!.visibility = View.VISIBLE
                        lineColor!!.setTextColor(mapWidget!!.flightPathColor)
                    }
                    2 -> {
                        width = mapWidget!!.flyZoneBorderWidth.toInt()
                        lineColor!!.visibility = View.GONE
                    }
                }
                lineWidthPicker!!.progress = width
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
    private fun addOverlay() {
        if (mapWidget!!.map == null) {
            Toast.makeText(applicationContext, R.string.error_map_not_initialized, Toast.LENGTH_SHORT).show()
            return
        }
        val testLat = 37.4419f
        val testLng = -122.1430f
        when (mapProvider) {
            0 -> if (mapOverlay == null) {
                hereMap = mapWidget!!.map.map as Map
                val overlayView = ImageView(this@MapWidgetActivity)
                overlayView.setImageDrawable(resources.getDrawable(R.drawable.ic_drone))
                val testLocation = GeoCoordinate(testLat.toDouble(), testLng.toDouble())
                mapOverlay = MapOverlay(overlayView, testLocation)
                hereMap!!.addMapOverlay(mapOverlay!!)
            } else {
                hereMap!!.removeMapOverlay(mapOverlay!!)
                mapOverlay = null
            }
            1 -> if (groundOverlay == null) {
                googleMap = mapWidget!!.map.map as GoogleMap
                val latLng1 = com.google.android.gms.maps.model.LatLng(testLat.toDouble(), testLng.toDouble())
                val latLng2 = com.google.android.gms.maps.model.LatLng(testLat + 0.25, testLng + 0.25)
                val latLng3 = com.google.android.gms.maps.model.LatLng(testLat - 0.25, testLng - 0.25)
                val bounds = LatLngBounds(latLng1, latLng2).including(latLng3)
                val aircraftImage = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(
                        resources,
                        R.drawable.ic_compass_aircraft))
                val groundOverlayOptions = GroundOverlayOptions()
                groundOverlayOptions.image(aircraftImage)
                        .positionFromBounds(bounds)
                        .transparency(0.5f)
                        .visible(true)
                groundOverlay = googleMap!!.addGroundOverlay(groundOverlayOptions)
            } else {
                groundOverlay!!.remove()
                groundOverlay = null
            }
            2 -> if (tileOverlay == null) {
                aMap = mapWidget!!.map.map as AMap
                val latlngs = arrayOfNulls<LatLng>(500)
                var i = 0
                while (i < 500) {
                    var x_: Double
                    var y_: Double
                    x_ = Math.random() * 0.5 - 0.25
                    y_ = Math.random() * 0.5 - 0.25
                    latlngs[i] = LatLng(testLat + x_, testLng + y_)
                    i++
                }
                val builder = HeatmapTileProvider.Builder()
                builder.data(Arrays.asList(*latlngs))
                val heatmapTileProvider = builder.build()
                val tileOverlayOptions = TileOverlayOptions()
                tileOverlayOptions.tileProvider(heatmapTileProvider).visible(true)
                tileOverlay = aMap!!.addTileOverlay(tileOverlayOptions)
            } else {
                tileOverlay!!.remove()
                tileOverlay = null
            }
        }
    }

    override fun onProgressChanged(seekBar: SeekBar, progressValue: Int, fromUser: Boolean) {
        lineWidthValue = progressValue
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {}
    override fun onStopTrackingTouch(seekBar: SeekBar) {
        if ("Home Direccion" == lineSpinner!!.selectedItem) {
            mapWidget!!.directionToHomeWidth = lineWidthValue.toFloat()
        } else if ("Ruta de Vuelo" == lineSpinner!!.selectedItem) {
            mapWidget!!.flightPathWidth = lineWidthValue.toFloat()
        } else if ("Frontera de Vuelo" == lineSpinner!!.selectedItem) {
            mapWidget!!.flyZoneBorderWidth = lineWidthValue.toFloat()
        }
    }

    private fun setRandomLineColor() {
        val rnd = Random()
        @ColorInt val randomColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
        lineColor!!.setTextColor(randomColor)
        if ("Home Direccion" == lineSpinner!!.selectedItem) {
            mapWidget!!.directionToHomeColor = randomColor
        } else if ("Zona de Vuelo" == lineSpinner!!.selectedItem) {
            mapWidget!!.flightPathColor = randomColor
        }
    }

    companion object {
        private const val TAG = "MapWidgetActivity"
        const val MAP_PROVIDER = "MapProvider"
        private fun getBitmap(vectorDrawable: VectorDrawable): Bitmap {
            val bitmap = Bitmap.createBitmap(vectorDrawable.intrinsicWidth,
                    vectorDrawable.intrinsicHeight,
                    Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
            vectorDrawable.draw(canvas)
            return bitmap
        }
    }
}