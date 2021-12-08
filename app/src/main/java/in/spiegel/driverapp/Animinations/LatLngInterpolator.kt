package `in`.spiegel.driverapp.Animinations

import com.google.android.gms.maps.model.LatLng
import java.lang.Math.*
import kotlin.math.pow


/**
 * Created by Vijay on 12/2/2021.
 */
 interface LatLngInterpolator {
     fun interpolate(fraction: Float, a: LatLng, b: LatLng): LatLng

     class Spherical : LatLngInterpolator {
        /* From github.com/googlemaps/android-maps-utils */
       override fun interpolate(fraction: Float, from: LatLng, to: LatLng): LatLng {

            // http://en.wikipedia.org/wiki/Slerp
            val fromLat: Double = toRadians(from.latitude)
            val fromLng: Double = toRadians(from.longitude)
            val toLat: Double = toRadians(to.latitude)
            val toLng: Double = toRadians(to.longitude)
            val cosFromLat: Double = kotlin.math.cos(fromLat)
            val cosToLat: Double = kotlin.math.cos(toLat)

            // Computes Spherical interpolation coefficients.
            val angle = computeAngleBetween(fromLat, fromLng, toLat, toLng)
            val sinAngle: Double = kotlin.math.sin(angle)

            if (sinAngle < 1E-6) {
                return from
            }

            val a: Double = kotlin.math.sin((1 - fraction) * angle) / sinAngle
            val b: Double = kotlin.math.sin(fraction * angle) / sinAngle

            // Converts from polar to vector and interpolate.

            val x: Double = a * cosFromLat * kotlin.math.cos(fromLng) + b * cosToLat * kotlin.math.cos(toLng)
            val y: Double = a * cosFromLat * kotlin.math.sin(fromLng) + b * cosToLat * kotlin.math.sin(toLng)

            val z: Double = a * kotlin.math.sin(fromLat) + b * kotlin.math.sin(toLat)
            // Converts interpolated vector back to polar.
            val lat: Double = kotlin.math.atan2(z, kotlin.math.sqrt(x * x + y * y))
            val lng: Double = kotlin.math.atan2(y, x)
            return LatLng(toDegrees(lat), toDegrees(lng))


        }

        private fun computeAngleBetween(
            fromLat: Double,
            fromLng: Double,
            toLat: Double,
            toLng: Double
        ): Double {
            // Haversine's formula
            val dLat: Double = fromLat - toLat
            val dLng = fromLng - toLng
            return 2 * kotlin.math.asin(
                kotlin.math.sqrt(
                    kotlin.math.sin(dLat / 2).pow(2.0) +
                            kotlin.math.cos(fromLat) * kotlin.math.cos(toLat) * kotlin.math.sin(dLng / 2)
                        .pow(2.0)
                )
            )
        }
    }
}