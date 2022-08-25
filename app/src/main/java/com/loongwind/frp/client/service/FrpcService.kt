package com.loongwind.frp.client.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.loongwind.frp.client.MainActivity
import com.loongwind.frp.client.R
import com.loongwind.frp.client.constant.*
import com.loongwind.frp.client.repository.IniRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class FrpcService : Service(), KoinComponent {

    private val iniRepository by inject<IniRepository>()

    private var process: Process? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
    }

    private fun startFrpc(id: Long) {
        val iniConfigContent = iniRepository.generateConfigContent(id)
        val applicationInfo =
            packageManager.getApplicationInfo(packageName, PackageManager.GET_SHARED_LIBRARY_FILES)
        this.openFileOutput(CONFIG_FILE_NAME, Context.MODE_PRIVATE).use {
            it.write(iniConfigContent.toByteArray())
        }
        process?.destroy()
        process = Runtime.getRuntime()
            .exec(
                "${applicationInfo.nativeLibraryDir}/$CMD_FILE_NAME -c $CONFIG_FILE_NAME",
                arrayOf(""),
                this.filesDir
            )
        showNotification()
    }

    private fun stopFrpc(){
        process?.destroy()
        stopForeground(true)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val id = intent?.getLongExtra(KEY_ID, 0) ?: 0
        val type = intent?.getIntExtra(KEY_TYPE, TYPE_START_SERVICE)
        if(type == TYPE_START_SERVICE){
            startFrpc(id)
        }else{
            stopFrpc()
        }
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        process?.destroy()
        process = null
        super.onDestroy()
    }


    private fun showNotification() {
        val pendingIntent: PendingIntent =
            Intent(this, MainActivity::class.java).let { notificationIntent ->
                PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)
            }
        val notification: Notification =
            NotificationCompat.Builder(this, createNotificationChannel("frpclient", "FrpcService"))
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("frp后台服务")
                .setContentText("已启动frp")
                .setContentIntent(pendingIntent)
                .build()
        startForeground(1, notification)
    }

    private fun createNotificationChannel(channelId: String, channelName: String): String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val chan = NotificationChannel(
                channelId,
                channelName, NotificationManager.IMPORTANCE_NONE
            )
            chan.lightColor = Color.BLUE
            chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            val service = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            service.createNotificationChannel(chan)
        }
        return channelId
    }
}