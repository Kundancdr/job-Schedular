package com.example.jobschedular.classes.service

import android.Manifest
import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.IBinder
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.jobschedular.R
import com.example.jobschedular.classes.singleton.emailandpass
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

//ForgroundServices

class ForgroundServices : Service() {

    companion object {
        private const val CHANNEL_ID = "ForegroundServiceChannel"
        private const val NOTIFICATION_CHANNEL_ID = "MyNotificationChannel"
        private const val NOTIFICATION_ID = 123
    }

    override fun onCreate() {
        super.onCreate()
    }

    @SuppressLint("ForegroundServiceType")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()

        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        FirebaseDatabase.getInstance().getReference("employ/${emailandpass.empid}/notificatiton")
            .addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val data = snapshot.value
                        val bo = data.toString()
                        if (bo.toBoolean()) {
                            showNotificationWithVibration(applicationContext, "task", "you have a new task ")
                            val vibrationEffect1: VibrationEffect

                            // this is the only type of the vibration which requires system version Oreo (API 26)
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                                // this effect creates the vibration of default amplitude for 1000ms(1 sec)
                                vibrationEffect1 =
                                    VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE)

                                // it is safe to cancel other vibrations currently taking place
                                vibrator.cancel()
                                vibrator.vibrate(vibrationEffect1)
                            }
                        }
                    } else
                        Log.e("not get ", "ni mela ")

                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e(TAG, "Failed to read data: ${error.message}")
                }
            })

        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("jobshaduler")
            .setContentText("jobshaduler service is running")
            .setSmallIcon(R.drawable.job)
            .build()
        startForeground(1, notification)

        // Your foreground service logic goes here

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun showNotificationWithVibration(context: Context, title: String, message: String) {

        createNotificationChannel(context)

        // Build the notification
        val notificationBuilder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.job)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)


        // Set vibration pattern
        notificationBuilder.setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))

        // Show the notification
        val notificationManager = NotificationManagerCompat.from(context)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
        }

    }

    private fun createNotificationChannel(context: Context) {
        // Create notification channel only for devices running Android Oreo and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "My Notification Channel"
            val descriptionText = "My Notification Channel Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }
}