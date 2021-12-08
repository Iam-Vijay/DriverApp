package `in`.spiegel.driverapp.Animinations

import android.os.Handler
import android.os.SystemClock

import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator

import com.google.android.gms.maps.model.LatLng

import com.google.android.gms.maps.model.Marker




/**
 * Created by Vijay on 12/2/2021.
 */
class MarkerAnimation {
    fun animateMarkerToGB(
        marker: Marker,
        finalPosition: LatLng?,
        latLngInterpolator: LatLngInterpolator
    ) {
        val startPosition = marker.position
        val handler = Handler()
        val start = SystemClock.uptimeMillis()
        val interpolator: Interpolator = AccelerateDecelerateInterpolator()
        val durationInMs = 2000f
        handler.post(object : Runnable {
            var elapsed: Long = 0
            var t = 0f
            var v = 0f
            override fun run() {
                // Calculate progress using interpolator
                elapsed = SystemClock.uptimeMillis() - start
                t = elapsed / durationInMs
                v = interpolator.getInterpolation(t)
                marker.position =
                    finalPosition?.let { latLngInterpolator.interpolate(v, startPosition, it) }!!

                // Repeat till progress is complete.
                if (t < 1) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16)
                }
            }
        })
    }
}