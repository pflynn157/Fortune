package org.patrickf4664.fortune.alarm

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.*

class AlarmManager {

    companion object {
        @JvmStatic
        fun setAlarm(activity: Activity, context: Context) {
            //Get the time
            val pref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
            val timeStr = pref.getString("notify-time", "9:00.AM")

            val c_index = timeStr.indexOf(":")
            val p_index = timeStr.indexOf(".")

            val hourStr = timeStr.substring(0, c_index)
            val minStr = timeStr.substring(c_index+1, p_index)
            val amPm = timeStr.substring(p_index+1)

            var hour = hourStr.toInt()
            val min = minStr.toInt()

            if (amPm == "PM") {
                hour += 12
            }

            //Set the alarm
            val alarmIntent = Intent(context, AlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0)

            val manager = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?

            val calendar = Calendar.getInstance()
            calendar.setTimeInMillis(System.currentTimeMillis())
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, min)
            calendar.set(Calendar.SECOND, 0)

            val time = calendar.timeInMillis
            manager!!.cancel(pendingIntent)
            manager!!.setRepeating(
                AlarmManager.RTC_WAKEUP, time,
                AlarmManager.INTERVAL_DAY, pendingIntent
            )
        }
    }
}