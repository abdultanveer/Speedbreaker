package com.example.speedbreaker.ui.home


import android.graphics.Color
import android.hardware.SensorEvent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.speedbreaker.R
import com.example.speedbreaker.databinding.FragmentHomeBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.example.speedbreaker.SensorActivity


class HomeFragment : Fragment() {
    var mpLineChart1: LineChart? = null




    private var _binding: FragmentHomeBinding? = null

   // private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // val root: View = inflater.inflate(R.layout.fragment_home,container,false)
            //binding.root
        mpLineChart1 = binding.chart1

        //val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
           // textView.text = it
        }
        return root
    }


    override fun onStart() {
        super.onStart()
        val lineDataSet1 = LineDataSet(dataValue1(), "the speed")
        val dataSet = ArrayList<ILineDataSet>()
        dataSet.add(lineDataSet1)
        lineDataSet1.color = Color.BLUE
        lineDataSet1.setCircleColor(Color.GREEN)
        lineDataSet1.isHighlightEnabled = true
        lineDataSet1.lineWidth = 2f
        lineDataSet1.circleRadius = 6f
        lineDataSet1.circleHoleRadius = 3f

        val data1 = LineData(dataSet)
        mpLineChart1!!.data = data1
        mpLineChart1!!.invalidate()
    }


        private fun dataValue1(): ArrayList<Entry>? {

     val accelerometer = ArrayList<Entry>()
      accelerometer.add(Entry(0.3418f, 9.7631f))
      accelerometer.add(Entry(-7.0033f, 6.752f))
      accelerometer.add(Entry(2.517f, 90.91657f))
     return accelerometer
 }



    override fun onDestroyView() {
        super.onDestroyView()
        //_binding = null
    }
}


