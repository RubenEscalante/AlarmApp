package com.udb.alarmapp.presentation.viewmodels

import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.SystemClock
import androidx.core.app.NotificationCompat
import com.udb.alarmapp.MainActivity
import com.udb.alarmapp.R
import javax.inject.Inject
import javax.inject.Singleton


class NotificationUtils(private val context: Context) {
    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun buildNotification(title: String, message: String, id: Int): Notification {
        // Create a notification channel (required for Android 8.0 and higher)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "my_channel_id",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)

        }
        // Crea una notificación
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.outline_wb_sunny_24)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        // Crea un intent para abrir la actividad principal cuando se toca la notificación
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        builder.setContentIntent(pendingIntent)

        return builder.build()
    }

    fun scheduleNotification(notification: Notification, id: Int, delay: Long) {
        // Programa una alarma para mostrar la notificación después del retraso especificado
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val triggerTime = SystemClock.elapsedRealtime() + delay
        val intent = Intent(context, MyBroadcastReceiver::class.java)
        intent.putExtra("title", notification.extras.getString(Notification.EXTRA_TITLE))
        intent.putExtra("message", notification.extras.getString(Notification.EXTRA_TEXT))
        intent.putExtra("id", id)
        val pendingIntent = PendingIntent.getBroadcast(
            context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, pendingIntent)
    }

    companion object {
        private const val CHANNEL_ID = "my_channel_id"
    }
}