package `in`.spiegel.driverapp.backgroundlocation

import `in`.spiegel.driverapp.R
import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*

/**
 * Created by Vijay on 12/3/2021.
 */
class BackgroundLocation :Service() {
    private val mBinder: IBinder = MyBinder()

    private val CHANNEL_ID = "2"


    override fun onBind(intent: Intent?): IBinder {
        return mBinder
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }


    override fun onCreate() {
        super.onCreate()
        buildNotification()
        requestLocationUpdates()
    }


    private fun buildNotification() {
        val stop = "stop"
        val broadcastIntent = PendingIntent.getBroadcast(
            this, 0, Intent(stop), PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Create the persistent notification
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(getString(R.string.app_name))
            .setContentText("Location tracking is working")
            .setOngoing(true)
            .setContentIntent(broadcastIntent)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID, getString(R.string.app_name),
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.setShowBadge(false)
            channel.description = "Location tracking is working"
            channel.setSound(null, null)
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
        startForeground(1, builder.build())
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("TAG", "Service Destroyed")
    }

    public fun requestLocationUpdates() {
        val request = LocationRequest()
        request.interval = 1000
        request.fastestInterval = 3000
        request.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        val client: FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(this)
        val permission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (permission == PackageManager.PERMISSION_GRANTED) {

            // Request location updates and when an update is
            // received, store the location in Firebase
            client.requestLocationUpdates(request, object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    val location =
                        """
                     Latitude : ${locationResult.getLastLocation().getLatitude().toString()}
                     Longitude : ${locationResult.getLastLocation().getLongitude()}
                     """.trimIndent()
                    Toast.makeText(this@BackgroundLocation, location, Toast.LENGTH_SHORT).show()
                }
            }, null)
        } else {
            stopSelf()
        }
    }

    class MyBinder : Binder() {
        val service: BackgroundLocation
            get() = BackgroundLocation()
    }
}