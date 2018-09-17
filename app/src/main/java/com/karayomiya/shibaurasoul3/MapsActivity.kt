package com.karayomiya.shibaurasoul3

import android.app.ActivityOptions
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.maps.*

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlin.concurrent.thread
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


        val shibaura = LatLng(35.951137, 139.653876)
        //mMap.addMarker(MarkerOptions().position(shibaura).title("？？？？？"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(shibaura))

        var zoom: Float = 1F
        var markerTapCnt = 0
        loadMap(zoom)
        setTitle("地図をタップして！")

        // 地図をタップすると拡大していく
        mMap.setOnMapClickListener { tapLocation ->

            zoom = zoom + 2
            if (zoom <= 15) {
                Log.d("zoom:", zoom.toString())
                loadMap(zoom)
                if (zoom == 15F) {
                    setTitle("マーカをタップして！")
                    mMap.addMarker(MarkerOptions().position(shibaura).title("芝浦大宮校舎！"))
                }
            } else {

            }
        }

        mMap.setOnMarkerClickListener { tapMarker ->
            markerTapCnt++
            if (markerTapCnt >= 2) {
                val intent = Intent(this, SubActivity::class.java)
                startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation(this@MapsActivity).toBundle())
            }
            return@setOnMarkerClickListener false
        }
        // なにかされても元に戻す
        mMap.setOnMapLongClickListener { loadMap(zoom) }
        mMap.setOnCameraMoveListener { loadMap(zoom) }
        mMap.setOnPoiClickListener { loadMap(zoom) }

        //mMap.onMarkerClick(Marker marker) {}
    }

    // とある座標を表示
    private fun loadMap(zoom : Float) {
        val shibaura = LatLng(35.951137, 139.653876)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(shibaura, zoom))
    }

}
