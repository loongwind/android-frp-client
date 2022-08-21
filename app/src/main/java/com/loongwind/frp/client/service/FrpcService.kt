package com.loongwind.frp.client.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.loongwind.frp.client.MainActivity
import com.loongwind.frp.client.R


class FrpcService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
    }

    private fun startFrp() {
        val ainfo =
            packageManager.getApplicationInfo(packageName, PackageManager.GET_SHARED_LIBRARY_FILES)

                this.openFileOutput("config.ini", Context.MODE_PRIVATE).use {
                    it.write("""
                        [common]
                        server_addr = 116.63.154.157
                        server_port = 7000
                        admin_port = 7400
                        admin_user = dxmh
                        admin_pwd = dxmh2020
                        log_file = ./frpc.log

                        [home-gateway]
                        type = tcp
                        local_ip = 192.168.1.1
                        local_port = 80
                        remote_port = 8881
                    """.trimIndent().toByteArray())
                }

        val p = Runtime.getRuntime()
            .exec(
                "${ainfo.nativeLibraryDir}/libfrpc.so -c config.ini",
                arrayOf(""),
                this.filesDir
            )
        showNotification()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startFrp()
        return START_NOT_STICKY
    }


    private fun showNotification() {
        val pendingIntent: PendingIntent =
            Intent(this, MainActivity::class.java).let { notificationIntent ->
                PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)
            }
        val notification: Notification = NotificationCompat.Builder(this, createNotificationChannel("frpclient", "FrpcService"))
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("frp后台服务")
            .setContentText("已启动frp")
            .setContentIntent(pendingIntent)
            .build()
        startForeground(1, notification)
    }

    private fun createNotificationChannel(channelId: String, channelName: String): String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val chan =NotificationChannel(
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