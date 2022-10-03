package com.example.speedbreaker.ui.dashboard

import android.Manifest
import android.app.ActivityManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.Sensor.*
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.speedbreaker.*
import com.example.speedbreaker.databinding.FragmentDashboardBinding
import com.google.android.gms.location.LocationResult

class DashboardFragment : Fragment(), View.OnClickListener, SensorEventListener {

    lateinit var  sensor: Sensor

    private val wordViewModel: WordViewModel by viewModels {
        WordViewModelFactory((activity?.application as WordsApplication).repository)
    }

    lateinit var  sensorManager :SensorManager
    lateinit var sensorEvent: SensorEvent

    lateinit var latLngTextView:TextView
    lateinit var speedTv: TextView

    lateinit var buttonPothole: Button
    lateinit var buttonHump: Button

   lateinit var xval: TextView
   lateinit var yval :TextView
   lateinit var zval :TextView

   lateinit var xGyroValue :TextView
   lateinit var yGyroValue :TextView
   lateinit var zGyroValue :TextView

   lateinit var xMagnoValue :TextView
   lateinit var yMagnoValue :TextView
   lateinit var zMagnoValue :TextView


   lateinit  var  accelerometer: Sensor
   lateinit var  btnStartLoc :Button
   lateinit var  btnStopLoc :Button



    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        sensorManager =  context?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(this, accelerometer,SensorManager.SENSOR_DELAY_NORMAL)

        var mGyro = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        sensorManager.registerListener(this, mGyro,SensorManager.SENSOR_DELAY_UI)

        var mMagno = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        sensorManager.registerListener(this, mMagno,SensorManager.SENSOR_DELAY_NORMAL)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       // setContentView(R.layout.fragment_dashboard)
        //sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager





    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this)[DashboardViewModel::class.java]

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

       // val textView: TextView = binding.textDashboard
        buttonHump = binding.btnHump
        buttonPothole = binding.btnPothole
        buttonHump.setOnClickListener(this)
        buttonPothole.setOnClickListener(this)

        xval= binding.xValue
        yval = binding.yValue
        zval = binding.zValue

        xGyroValue = binding.xGyroValue
        yGyroValue = binding.yGyroValue
        zGyroValue = binding.zGyroValue

        xMagnoValue = binding.xMagnoValue
        yMagnoValue = binding.yMagnoValue
        zMagnoValue = binding.zMagnoValue

        btnStartLoc= binding.buttonStartLocationUpdates
        btnStopLoc = binding.buttonStopLocationUpdates
        btnStopLoc.setOnClickListener(this)
        btnStartLoc.setOnClickListener(this)


        latLngTextView = binding.tvLatlong
        speedTv = binding.tvSpeed
        

        dashboardViewModel.text.observe(viewLifecycleOwner) {
            //textView.text = it
        }
        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onClick(viewClicked: View?) {

        when(viewClicked?.id){
            R.id.btnHump ->{
                var gyroscopeValues: String = ""
                if(sensor.type == Sensor.TYPE_GYROSCOPE) {
                     gyroscopeValues = "x="+sensorEvent.values[0]+",y="+sensorEvent.values[1]+",z="+sensorEvent.values[2]
                }
                var word: Word = Word(123, gyroscopeValues,"---" ,"nnn","mmm","000","ppp","qqq","rrr","sss")
                wordViewModel.insert(word)
                Log.i(TAG, "onClick: hump detected--accelerometer =")
                Log.d(TAG,"TYPE_ACCELEROMETER: X: " + sensorEvent!!.values[0] + "Y: " + sensorEvent!!.values[1] + "Z: " + sensorEvent!!.values[2])

                Log.i(TAG, "onClick: hump detected--Gyroscope =")
                Log.d(TAG,"TYPE_GYROSCOPE: X: " + sensorEvent!!.values[0] + "Y: " + sensorEvent!!.values[1] + "Z: " + sensorEvent!!.values[2])

                Log.i(TAG, "onClick: hump detected--Magnetic Field =")
                Log.d(TAG,"TYPE_MAGNETIC_FIELD: X: " + sensorEvent!!.values[0] + "Y: " + sensorEvent!!.values[1] + "Z: " + sensorEvent!!.values[2])

            }

            R.id.btnPothole -> {Log.i(TAG, "onClick: pothole detected")}

            R.id.buttonStartLocationUpdates -> {
                if (context?.applicationContext?.let {
                        ContextCompat.checkSelfPermission(
                            it, Manifest.permission.ACCESS_FINE_LOCATION
                        )
                    } != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        DashboardFragment.REQUEST_CODE_LOCATION_PERMISSION
                    )
                } else {
                    startLocationService()
                }
            }
            R.id.buttonStopLocationUpdates -> { stopLocationService() }

        }


    }
    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(requireContext())
            .registerReceiver(mReceiver, IntentFilter("location.action"))
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == DashboardFragment.REQUEST_CODE_LOCATION_PERMISSION && grantResults.size > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationService()
            } else {
                Toast.makeText(activity, "Permission denied!", Toast.LENGTH_SHORT).show()
                //Log.i(TAG,"Permission Denied")
            }
        }
    }

    private val isLocationServiceRunning: Boolean
        private get() {
            val activityManager = context?.getSystemService(AppCompatActivity.ACTIVITY_SERVICE) as ActivityManager
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
    private fun stopLocationService() {
        if (isLocationServiceRunning) {
            val intent = Intent(context?.applicationContext, LocationService::class.java)
            intent.action = Constants.ACTION_STOP_LOCATION_SERVICE
            context?.stopService(intent)
            Toast.makeText(activity, "Location Service stopped", Toast.LENGTH_SHORT).show()
            //Log.i(TAG,"Location Service Stopped")
        }


    }


    private fun startLocationService() {
        // if (isLocationServiceRunning) {
        val intent = Intent(context?.applicationContext, LocationService::class.java)
        intent.action = Constants.ACTION_START_LOCATION_SERVICE
        context?.startService(intent)
        //Log.i(TAG,"Location Service started")
        Toast.makeText(activity, "Location Service started", Toast.LENGTH_SHORT).show()
        //  }
    }

    private val mReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val updateno = intent.extras!!.getInt("updateno")
            val locationResult: LocationResult? = intent.extras!!.getParcelable("location")
            Log.d(
                LocationActivity.TAG,
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


    companion object{
        private const val REQUEST_CODE_LOCATION_PERMISSION = 1

        var TAG = DashboardFragment::class.java.simpleName
    }






    
    
    override fun onSensorChanged(senseEvent: SensorEvent?) {
        sensorEvent = senseEvent!!
         sensor = senseEvent!!.sensor
        if (sensor.type == Sensor.TYPE_ACCELEROMETER) {
            xval.setText(sensorEvent.values[0].toString())
            yval.setText(sensorEvent.values[1].toString())
            zval.setText(sensorEvent.values[2].toString())
            Log.d(TAG,"TYPE_ACCELEROMETER: X: " + senseEvent!!.values[0] + "Y: " + senseEvent!!.values[1] + "Z: " + senseEvent!!.values[2])

        }
        else if (sensor.type == Sensor.TYPE_GYROSCOPE) {
            xGyroValue.setText(sensorEvent.values[0].toString())
            yGyroValue.setText(sensorEvent.values[1].toString())
            zGyroValue.setText(sensorEvent.values[2].toString())
            Log.d(TAG,"TYPE_GYROSCOPE: X: " + senseEvent!!.values[0] + "Y: " + senseEvent!!.values[1] + "Z: " + senseEvent!!.values[2])

        }
        else {
            xMagnoValue.setText(sensorEvent.values[0].toString())
            yMagnoValue.setText(sensorEvent.values[1].toString())
            zMagnoValue.setText(sensorEvent.values[2].toString())
            Log.d(TAG,"TYPE_MAGNETIC_FIELD: X: " + senseEvent!!.values[0] + "Y: " + senseEvent!!.values[1] + "Z: " + senseEvent!!.values[2])

        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

}
