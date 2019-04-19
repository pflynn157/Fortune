package org.patrickf4664.fortune


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import org.patrickf4664.fortune.alarm.AlarmManager
import java.util.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SettingsFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var hourEntry : EditText
    private lateinit var minEntry : EditText
    private lateinit var amPmEntry : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hourEntry = view.findViewById(R.id.hour_entry)
        minEntry = view.findViewById(R.id.min_entry)
        amPmEntry = view.findViewById(R.id.am_pm_entry)

        view.findViewById<Button>(R.id.set_time_btn).setOnClickListener {
            setTime()
        }

        val calender = Calendar.getInstance().time
        var hour = calender.hours
        val min = calender.minutes
        var amPm = "AM"

        if (hour > 12) {
            hour -= 12
            amPm = "PM"
        }

        hourEntry.setText(hour.toString())
        minEntry.setText(min.toString())
        amPmEntry.setText(amPm)
    }

    private fun setTime() {
        val strHour = hourEntry.text.toString()
        val strMin = minEntry.text.toString()
        var amPm = amPmEntry.text.toString()

        val hour = strHour.toIntOrNull()
        val min = strMin.toIntOrNull()

        if (hour != null) {
            if (hour < 1 || hour > 12) {
                Toast.makeText(this.context, "Error: Invalid hour.", Toast.LENGTH_SHORT).show()
                return
            }
        }

        if (min != null) {
            if (min < 1 || min > 60) {
                Toast.makeText(this.context, "Error: Invalid minute.", Toast.LENGTH_SHORT).show()
                return
            }
        }

        if (!amPm.equals("AM",true) && !amPm.equals("PM",true)) {
            Toast.makeText(this.context, "Error: Please choose either \"AM\" or \"PM\"",
                Toast.LENGTH_SHORT).show()
            return
        }

        var time = strHour + ":" + strMin + "." + amPm.toUpperCase()
        val pref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with (pref.edit()) {
            putString("notify-time", time)
            commit()
        }

        AlarmManager.setAlarm(this.activity!!, this.context!!)
        Toast.makeText(this.context, "Notification set", Toast.LENGTH_SHORT).show()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
