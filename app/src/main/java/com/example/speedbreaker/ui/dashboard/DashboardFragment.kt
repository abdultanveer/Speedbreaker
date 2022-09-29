package com.example.speedbreaker.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.speedbreaker.R
import com.example.speedbreaker.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment(), View.OnClickListener {

   lateinit var buttonPothole: Button
   lateinit var buttonHump: Button

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        buttonHump = binding.btnHump
        buttonPothole = binding.btnPothole
        buttonHump.setOnClickListener(this)
        buttonPothole.setOnClickListener(this)
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
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
                Log.i(TAG, "onClick: hump detected")}
            R.id.btnPothole -> {Log.i(TAG, "onClick: pothole detected")}

        }
    }

    companion object{
        var TAG = DashboardFragment::class.java.simpleName
    }
}