package com.almaz.mukatukha_drinks.ui.cafe

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.almaz.itis_booking.utils.ViewModelFactory
import com.almaz.mukatukha_drinks.App
import com.almaz.mukatukha_drinks.R
import com.almaz.mukatukha_drinks.ui.base.BaseFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject


class MapFragment : BaseFragment(), OnMapReadyCallback {
    @Inject
    lateinit var viewModeFactory: ViewModelFactory
    private lateinit var viewModel: MapViewModel

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent
            .mapComponent()
            .withActivity(activity as AppCompatActivity)
            .build()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_google_maps, container, false)
        init()
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbarAndBottomNavVisibility(
            toolbarVisibility = View.GONE,
            bottomNavVisibility = View.VISIBLE
        )
    }

    private fun init() {
        viewModel = ViewModelProvider(this, this.viewModeFactory)
            .get(MapViewModel::class.java)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        /*val mapFragment = childFragmentManager
            .findFragmentById(R.id.google_maps) as SupportMapFragment
        mapFragment.getMapAsync(this)*/
        getLocationPermission()
    }

    /*override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))    }*/

    override fun onMapReady(googleMap: GoogleMap) {
        Toast.makeText(rootActivity.applicationContext, "Map is Ready", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "onMapReady: map is ready")
        mMap = googleMap
        if (mLocationPermissionsGranted) {
            getDeviceLocation()
            if (ActivityCompat.checkSelfPermission(
                    rootActivity.applicationContext,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    rootActivity.applicationContext,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            mMap.isMyLocationEnabled = true
            mMap.uiSettings.isMyLocationButtonEnabled = true

        }
    }

    private val TAG = "MapActivity"

    private val FINE_LOCATION: String = Manifest.permission.ACCESS_FINE_LOCATION
    private val COURSE_LOCATION: String = Manifest.permission.ACCESS_COARSE_LOCATION
    private val LOCATION_PERMISSION_REQUEST_CODE = 1234
    private val DEFAULT_ZOOM = 15f

    //vars
    private var mLocationPermissionsGranted = false
    private lateinit var mFusedLocationProviderClient: FusedLocationProviderClient

    private fun getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: getting the devices current location")
        mFusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(activity as AppCompatActivity)
        try {
            if (mLocationPermissionsGranted) {
                val location =
                    mFusedLocationProviderClient.lastLocation
                        location.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "onComplete: found location!")
                            val currentLocation: Location? = task.result as Location?
                            moveCamera(
                                LatLng(
                                    currentLocation?.latitude!!,
                                    currentLocation.longitude
                                ),
                                DEFAULT_ZOOM
                            )
                        } else {
                            Log.d(TAG, "onComplete: current location is null")
                            Toast.makeText(
                                rootActivity.applicationContext,
                                "unable to get current location",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        } catch (e: SecurityException) {
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.message)
        }
    }

    private fun moveCamera(latLng: LatLng, zoom: Float) {
        Log.d(
            TAG,
            "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))
    }

    private fun initMap() {
        Log.d(TAG, "initMap: initializing map")
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.google_maps) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun getLocationPermission() {
        Log.d(TAG, "getLocationPermission: getting location permissions")
        val permissions = arrayOf<String>(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if (ContextCompat.checkSelfPermission(
                rootActivity.applicationContext,
                FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            if (ContextCompat.checkSelfPermission(
                    rootActivity.applicationContext,
                    COURSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                mLocationPermissionsGranted = true
                initMap()
            } else {
                ActivityCompat.requestPermissions(
                    activity as AppCompatActivity,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            }
        } else {
            ActivityCompat.requestPermissions(
                activity as AppCompatActivity,
                permissions,
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        Log.d(TAG, "onRequestPermissionsResult: called.")
        mLocationPermissionsGranted = false
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty()) {
                    var i = 0
                    while (i < grantResults.size) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false
                            Log.d(TAG, "onRequestPermissionsResult: permission failed")
                            return
                        }
                        i++
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted")
                    mLocationPermissionsGranted = true
                    //initialize our map
                    initMap()
                }
            }
        }
    }

}
