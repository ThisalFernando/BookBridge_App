package com.example.bookdonationapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private val LOCATION_PERMISSION_REQUEST_CODE = 1
    private lateinit var myMap: GoogleMap
    private lateinit var apiKey: String

    interface DirectionsApi {
        @GET("maps/api/directions/json")
        suspend fun getDirections(
            @Query("origin") origin: String,
            @Query("destination") destination: String,
            @Query("key") apiKey: String
        ): retrofit2.Response<DirectionsResponse>
    }

    data class DirectionsResponse(val status: String, val routes: List<Route>)
    data class Route(val overview_polyline: OverviewPolyline)
    data class OverviewPolyline(val points: String)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        // Retrieve the API key from the manifest
        apiKey = getString(R.string.google_maps_key)

        // Check location permissions
        checkLocationPermission()

        // Setup button click listener
        findViewById<ImageButton>(R.id.button_route).setOnClickListener {
            showRoute()
        }

        // Setup button click listeners for zoom
        findViewById<Button>(R.id.button_zoom_in).setOnClickListener {
            zoomIn()
        }

        findViewById<Button>(R.id.button_zoom_out).setOnClickListener {
            zoomOut()
        }
    }

    private fun zoomOut() {
        myMap.animateCamera(CameraUpdateFactory.zoomOut())
    }

    private fun zoomIn() {
        myMap.animateCamera(CameraUpdateFactory.zoomIn())
    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE)
        } else {
            if (isGooglePlayServicesAvailable()) {
                initializeMap()
            }
        }
    }

    private fun isGooglePlayServicesAvailable(): Boolean {
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        val resultCode = googleApiAvailability.isGooglePlayServicesAvailable(this)
        return resultCode == ConnectionResult.SUCCESS
    }

    private fun initializeMap() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                initializeMap()
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        myMap = googleMap
        myMap.mapType = GoogleMap.MAP_TYPE_NORMAL

        // Enable location layer if permission is granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            myMap.isMyLocationEnabled = true
        }

        // Get city data from intent
        val cityName = intent.getStringExtra("CITY_NAME") ?: "Unknown Location"
        val latitude = intent.getDoubleExtra("LATITUDE", 0.0)
        val longitude = intent.getDoubleExtra("LONGITUDE", 0.0)

        // Define the location and add a marker
        val cityLocation = LatLng(latitude, longitude)
        myMap.addMarker(MarkerOptions().position(cityLocation).title(cityName))
        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cityLocation, 10f)) // Zoom level
    }

    private fun showRoute() {
        val myLocation = myMap.myLocation
        if (myLocation != null) {
            val origin = "${myLocation.latitude},${myLocation.longitude}"
            val destination = "${intent.getDoubleExtra("LATITUDE", 0.0)},${intent.getDoubleExtra("LONGITUDE", 0.0)}"

            Log.d("MapActivity", "Current location fetched: $origin")
            Log.d("MapActivity", "Destination location: $destination")

            // Initialize Retrofit
            val retrofit = Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val directionsApi = retrofit.create(DirectionsApi::class.java)

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = directionsApi.getDirections(origin, destination, apiKey)
                    Log.d("MapActivity", "Response Status: ${response.body()?.status}")
                    if (response.isSuccessful && response.body()?.status == "OK") {
                        val route = response.body()?.routes?.firstOrNull()
                        if (route != null) {
                            val polyline = route.overview_polyline.points

                            // Switch to the main thread to update the UI
                            withContext(Dispatchers.Main) {
                                drawPolyline(polyline)
                                Log.d("MapActivity", "Route shown successfully.")
                            }
                        } else {
                            Log.e("MapActivity", "No routes found")
                        }
                    } else {
                        Log.e("MapActivity", "Error: ${response.errorBody()?.string()}")
                    }
                } catch (e: Exception) {
                    Log.e("MapActivity", "Exception: ${e.message}")
                }
            }
        } else {
            Log.e("MapActivity", "Current location not available.")
        }
    }

    private fun drawPolyline(encoded: String) {
        val path = decodePoly(encoded)
        val polylineOptions = PolylineOptions().addAll(path).color(android.graphics.Color.BLUE).width(20f)
        myMap.addPolyline(polylineOptions)
    }

    private fun decodePoly(encoded: String): List<LatLng> {
        val poly = mutableListOf<LatLng>()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0

        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0

            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1F shl shift)
                shift += 5
            } while (b >= 0x20)

            val dlat = if (result and 1 != 0) {
                -(result shr 1)
            } else {
                result shr 1
            }
            lat += dlat

            result = 0
            shift = 0

            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1F shl shift)
                shift += 5
            } while (b >= 0x20)

            val dlng = if (result and 1 != 0) {
                -(result shr 1)
            } else {
                result shr 1
            }
            lng += dlng

            val latLng = LatLng((lat / 1E5), (lng / 1E5))
            poly.add(latLng)
        }
        return poly
    }
}
