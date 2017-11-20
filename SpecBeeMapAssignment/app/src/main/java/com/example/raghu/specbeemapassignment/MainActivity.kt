package com.example.raghu.specbeemapassignment

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.raghu.dagger2testandroid.presenter.MainActivityPresenter
import com.example.raghu.dagger2testandroid.presenter.MainPresenterContract
import com.example.raghu.specbeemapassignment.models.Example
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.android.AndroidInjection
import javax.inject.Inject
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition



class MainActivity : AppCompatActivity(), OnMapReadyCallback, MainPresenterContract.View {


    private var  mapready: Boolean = false
    private lateinit var map:GoogleMap
    @Inject
    lateinit var mainPresenter: MainActivityPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_main)


        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        mainPresenter.doSomething("Bangalore","AIzaSyAZFybetiE-98o4Ky3pUlojqHbo4W9FjsM")
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     *
     */
    override fun onMapReady(map: GoogleMap) {
        mapready = true
        this.map = map
    }

    override fun showData(example: Example) {

        val lat = example.results.get(0).geometry.location.lat
        val lng = example.results.get(0).geometry.location.lng

        if(mapready) {

            this.map.addMarker(MarkerOptions().position(LatLng(lat, lng)).title("Marker"))

            val BANGALORE_VIEW = LatLng(lat, lng)
            val cameraPosition = CameraPosition.Builder()
                    .target(BANGALORE_VIEW)      // Sets the center of the map to Mountain View
                    .zoom(17f)                   // Sets the zoom
                    .build()                   // Creates a CameraPosition from the builder
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        }
    }



}
