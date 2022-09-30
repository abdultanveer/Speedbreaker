package com.example.speedbreaker.ui.dashboard

import android.content.Context
import android.hardware.Sensor
import android.hardware.Sensor.*
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.speedbreaker.R
import com.example.speedbreaker.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment(), View.OnClickListener, SensorEventListener {
    lateinit var  sensorManager :SensorManager
    lateinit var sensorEvent: SensorEvent

   lateinit var buttonPothole: Button
   lateinit var xval: TextView
   lateinit var yval :TextView
   lateinit var zval :TextView
   lateinit var xGyroValue :TextView
   lateinit var yGyroValue :TextView
   lateinit var zGyroValue :TextView
   lateinit var xMagnoValue :TextView
   lateinit var yMagnoValue :TextView
   lateinit var zMagnoValue :TextView
   lateinit var mGyro :Sensor
    lateinit  var  accelerometer: Sensor


    lateinit var buttonHump: Button

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       // setContentView(R.layout.fragment_dashboard)
      sensorManager =  context?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        //sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager


          accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(this, accelerometer,SensorManager.SENSOR_DELAY_NORMAL)

        var mGyro = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        sensorManager.registerListener(this, mGyro,SensorManager.SENSOR_DELAY_NORMAL)

        var mMagno = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        sensorManager.registerListener(this, mMagno,SensorManager.SENSOR_DELAY_NORMAL)


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
        xval= binding.xValue
        yval = binding.yValue
        zval = binding.zValue
        xGyroValue = binding.xGyroValue
        yGyroValue = binding.yGyroValue
        zGyroValue = binding.zGyroValue
        xMagnoValue = binding.xMagnoValue
        yMagnoValue = binding.yMagnoValue
        zMagnoValue = binding.zMagnoValue



        buttonPothole = binding.btnPothole
        buttonHump.setOnClickListener(this)
        buttonPothole.setOnClickListener(this)

      /*  yval.setText(accelerometer)
        zval.setText(accelerometer)
        xGyroValue.setText(mGyro)
        yGyroValue.setText(mGyro)
        zGyroValue.setText(mGyro)

        xMagnoValue.setText(TYPE_MAGNETIC_FIELD)
        yMagnoValue.setText(TYPE_MAGNETIC_FIELD)
        zMagnoValue.setText(TYPE_MAGNETIC_FIELD)*/


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

                Log.i(TAG, "onClick: hump detected--accelerometer =")
                Log.d(TAG,"TYPE_ACCELEROMETER: X: " + sensorEvent!!.values[0] + "Y: " + sensorEvent!!.values[1] + "Z: " + sensorEvent!!.values[2])

                Log.i(TAG, "onClick: hump detected--Gyroscope =")
                Log.d(TAG,"TYPE_GYROSCOPE: X: " + sensorEvent!!.values[0] + "Y: " + sensorEvent!!.values[1] + "Z: " + sensorEvent!!.values[2])

                Log.i(TAG, "onClick: hump detected--Magnetic Field =")
                Log.d(TAG,"TYPE_MAGNETIC_FIELD: X: " + sensorEvent!!.values[0] + "Y: " + sensorEvent!!.values[1] + "Z: " + sensorEvent!!.values[2])


            }

            R.id.btnPothole -> {Log.i(TAG, "onClick: pothole detected")}

        }
    }

    companion object{
        var TAG = DashboardFragment::class.java.simpleName
    }

    override fun onSensorChanged(senseEvent: SensorEvent?) {
        sensorEvent = senseEvent!!
        val sensor = senseEvent!!.sensor
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
