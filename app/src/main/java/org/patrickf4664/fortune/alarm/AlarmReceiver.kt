package org.patrickf4664.fortune.alarm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat

import org.patrickf4664.fortune.R
import org.patrickf4664.fortune.StorageManager

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        var manager = context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val name = "Your Fortune"
        val text = StorageManager.getQuote(context)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("fortune_chn", name, importance).apply {
                description = text
            }
            // Register the channel with the system
            manager.createNotificationChannel(channel)
        }


        var top = text
        if (text.length >= 20) {
            top = text.substring(0, 20)
        }

        val builder = NotificationCompat.Builder(context, "fortune_chn")
        builder.setDefaults(Notification.DEFAULT_ALL)
        builder.setContentTitle(name)
        builder.setContentText(top)
        builder.setSmallIcon(R.drawable.app_icon_small)
        builder.setPriority(Notification.PRIORITY_HIGH)
        builder.setStyle(NotificationCompat.BigTextStyle().bigText(text))

        val notification = builder.build()

        manager.notify(100, notification)
    }
}