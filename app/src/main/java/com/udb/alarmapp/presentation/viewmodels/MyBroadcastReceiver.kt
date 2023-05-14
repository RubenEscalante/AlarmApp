package com.udb.alarmapp.presentation.viewmodels

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.udb.alarmapp.MainActivity
import com.udb.alarmapp.R

class MyBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("title") ?: context.getString(R.string.notification_title)
        val message = intent.getStringExtra("message") ?: context.getString(R.string.notification_message)
        val id = intent.getStringExtra("id")?.toInt() ?: 0
        // Construye la notificación y la muestra
        val utils = NotificationUtils(context)
        val notification = utils.buildNotification(title, message,id)
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancelAll()
        notificationManager.notify(id, notification)

    }

    companion object {
        private const val NOTIFICATION_ID = 0
    }
}