package com.example.speedbreaker

import android.Manifest
import android.app.ActivityManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.location.LocationResult

class LocationActivity : AppCompatActivity() {
    lateinit var latLngTextView:TextView
    lateinit var speedTv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)//........................................
        latLngTextView = findViewById(R.id.tvLatlong)
        speedTv = findViewById(R.id.tvSpeed)
        findViewById<View>(R.id.buttonStartLocationUpdates).setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    applicationContext, Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this@LocationActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_CODE_LOCATION_PERMISSION
                )
            } else {
                startLocationService()
            }
        }
        findViewById<View>(R.id.buttonStopLocationUpdates).setOnClickListener { stopLocationService() }
    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(mReceiver, IntentFilter("location.action"))
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.size > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationService()
            } else {
                Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val isLocationServiceRunning: Boolean
        private get() {
            val activityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
            if (activityManager != null) {
                for (service in activityManager.getRunningServices(Int.MAX_VALUE)) {
                    if (LocationService::class.java.getName() == service.service.className) {
                        if (service.foreground) {
                            return true
                        }
                    }
                }
                return true
            }
            return false
        }

    private fun startLocationService() {
       // if (isLocationServiceRunning) {
            val intent = Intent(applicationContext, LocationService::class.java)
            intent.action = Constants.ACTION_START_LOCATION_SERVICE
            startService(intent)
            Toast.makeText(this, "Location Service started", Toast.LENGTH_SHORT).show()
      //  }
    }

    private fun stopLocationService() {
        if (isLocationServiceRunning) {
            val intent = Intent(applicationContext, LocationService::class.java)
            intent.action = Constants.ACTION_STOP_LOCATION_SERVICE
            stopService(intent)
            Toast.makeText(this, "Location Service stoped", Toast.LENGTH_SHORT).show()
        }
    }

    private val mReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val updateno = intent.extras!!.getInt("updateno")
            val locationResult: LocationResult? = intent.extras!!.getParcelable("location")
            Log.d(
                TAG,
                "Recieved message by BroadcastReciever: latitude =" + locationResult?.getLastLocation()?.getLatitude()
            )
            val location: Location = locationResult?.getLastLocation()!!
            speedTv.setText(location.speed.toString()+"-m/s")
            latLngTextView.text = """latitude =${location.latitude}
                longitude =${location.longitude}
                --$updateno
                accuaracy${location.accuracy}
                speed${location.speed}
                """.trimIndent()



        }
    }

    companion object {
        private const val REQUEST_CODE_LOCATION_PERMISSION = 1
        var TAG = MainActivity::class.java.simpleName
    }
}