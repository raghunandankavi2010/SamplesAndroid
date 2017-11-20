package com.example.raghu.specbeemapassignment

import android.app.Activity
import android.content.Intent
import android.media.MediaScannerConnection
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.util.Log
import android.view.Menu
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
import kotlinx.android.synthetic.main.activity_main.*
import android.view.MenuInflater
import android.view.MenuItem
import java.io.File
import android.support.v4.view.MenuItemCompat
import android.support.v4.view.MenuItemCompat.setOnActionExpandListener
import android.support.v7.widget.SearchView
import android.text.TextUtils
import android.view.inputmethod.InputMethodManager


class MainActivity : AppCompatActivity(), OnMapReadyCallback, MainPresenterContract.View, SearchView.OnQueryTextListener {
    override fun onQueryTextSubmit(query: String): Boolean {

        hideKeyboardwithoutPopulate(this@MainActivity)
        if(!TextUtils.isEmpty(query)) {
            title = query
            mainPresenter.doSomething(query, "AIzaSyAZFybetiE-98o4Ky3pUlojqHbo4W9FjsM")
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }


    private var mapready: Boolean = false
    private lateinit var map: GoogleMap
    @Inject
    lateinit var mainPresenter: MainActivityPresenter
    private var address: String? = null
    private var title: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_main)


        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


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
        //lat=35&lon=139
        mainPresenter.getWeatherData(lat, lng, "44e14f23d025b2c43ff31b322b4080fb")
        address = example.results.get(0).formattedAddress

        if (mapready) {

            this.map.addMarker(MarkerOptions().position(LatLng(lat, lng)).title(title))

            val BANGALORE_VIEW = LatLng(lat, lng)
            val cameraPosition = CameraPosition.Builder()
                    .target(BANGALORE_VIEW)      // Sets the center of the map to Mountain View
                    .zoom(17f)                   // Sets the zoom
                    .build()                   // Creates a CameraPosition from the builder
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        }
    }

    override fun showDataWeather(example: com.example.raghu.specbeemapassignment.models.weather.Example) {
        Log.i("MainActivvity", "" + example.main.temp)
        //temperatureValue.setText((result) + " \u2109");
        addressinfo.text = address
        temp.text = "" + example.main.temp + "\u2103"

    }


    override fun onDestroy() {
        super.onDestroy()
        mainPresenter.unSubscribe()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        val item = menu.findItem(R.id.search)
        val searchView = MenuItemCompat.getActionView(item) as SearchView
        searchView.setOnQueryTextListener(this@MainActivity)

        MenuItemCompat.setOnActionExpandListener(item,
                object : MenuItemCompat.OnActionExpandListener {
                    override fun onMenuItemActionCollapse(item: MenuItem): Boolean {

                        return true // Return true to collapse action view
                    }

                    override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                        // Do something when expanded
                        return true // Return true to expand action view
                    }
                })
        return true
    }

    /*  override fun onOptionsItemSelected(item: MenuItem): Boolean {

          when (item.getItemId()) {

              R.id.search -> {
                  val intent = Intent(this@MainActivity,SearchActivity::class.java)
                  startActivityForResult(intent,MainActivity.REQUEST_RESULT)
                  return true
              }

              else -> { // Note the block
                  return super.onOptionsItemSelected(item)
              }
          }
      }*/

    fun hideKeyboardwithoutPopulate(activity: Activity) {
        val inputMethodManager = activity.getSystemService(
                Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
                activity.currentFocus!!.windowToken, 0)
    }
}
