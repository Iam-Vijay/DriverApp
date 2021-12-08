package `in`.spiegel.driverapp.UI.ui.Fragments

import `in`.spiegel.driverapp.BaseFragment
import `in`.spiegel.driverapp.R
import `in`.spiegel.driverapp.databinding.FragmentHome2Binding
import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.location.*
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import java.lang.Exception
import com.google.android.gms.maps.model.BitmapDescriptorFactory

import android.graphics.Bitmap
import android.graphics.Canvas

import androidx.core.content.ContextCompat

import android.graphics.drawable.Drawable

import com.google.android.gms.maps.model.BitmapDescriptor

import com.google.android.gms.maps.model.MarkerOptions
import android.widget.Toast

import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.os.CountDownTimer
import java.text.DecimalFormat
import java.text.NumberFormat


class HomeFragment : BaseFragment(),LocationListener {
    lateinit var locationManager: LocationManager
    private val MIN_TIME: Long = 0
    private val MIN_DISTANCE = 0f
    lateinit var binding:FragmentHome2Binding
    lateinit var googleMap: GoogleMap
    var mLocationRequest: LocationRequest? = null
    var latLng = LatLng(0.0,0.0)

    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->
            this.googleMap = googleMap
        googleMap.isMyLocationEnabled = true

    }

    @SuppressLint("MissingPermission")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHome2Binding.inflate(layoutInflater,container,false)
        locationManager = context?.getSystemService(LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, this)
        //
        val animSlideDown: Animation =
            AnimationUtils.loadAnimation(context, R.anim.slide_down)
        val animSlideUp: Animation =
            AnimationUtils.loadAnimation(context, R.anim.slide_down)


        //You can also use LocationManager.GPS_PROVIDER and LocationManager.PASSIVE_PROVIDER
        binding.starttrip.setSlideButtonListener {



        }
        binding.accept.setOnClickListener {
            binding.starttrip.visibility = View.VISIBLE
        }
//        binding.accept.setOnClickListener {
//            binding.header.startAnimation(animSlideUp)
//            binding.header.visibility = View.GONE
//            binding.content.stopRippleAnimation()
//            binding.content.visibility = View.GONE
//            val firstlatlng = LatLng(11.02134,76.98354833333333)
//
//            googleMap.addMarker(
//                MarkerOptions().position(firstlatlng)
//                    .title("Customer Location") // below line is use to add custom marker on our map.
//                    .icon(
//                        context?.let {
//                            BitmapFromVector(
//                                it,
//                                R.drawable.icons8_standing_man_48px
//                            )
//                        }
//                    )
//            )
//
//            zoomMapInitial(firstlatlng,latLng)
//        }
//

        object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Used for formatting digit to be in 2 digits only
                val f: NumberFormat = DecimalFormat("00")
                val hour = millisUntilFinished / 3600000 % 24
                val min = millisUntilFinished / 60000 % 60
                val sec = millisUntilFinished / 1000 % 60
                binding.timertext.text =  f.format(sec)
            }

            // When the task is over it will print 00:00:00 there
            override fun onFinish() {
                tripisComing()

            }
        }.start()
        return binding.root

    }
        fun tripisComing(){
            val animSlideDown: Animation =
                AnimationUtils.loadAnimation(context, R.anim.slide_down)
            binding.orderlayout.visibility = View.VISIBLE
            binding.orderlayout.animation = animSlideDown
            binding.address.text = " 19b,Surendaran nagar 6th street,Kalavasal ,Madurai ,Tamilnadu - 645010 "
            binding.accept.visibility = View.VISIBLE
            binding.reject.visibility = View.VISIBLE
            binding.timerlayout.visibility = View.VISIBLE
            startTimer()
        }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

    }

    override fun onLocationChanged(location: Location) {
        val latLng = LatLng(location.latitude, location.longitude)
        Log.e("tag",latLng.toString())
        this.latLng = latLng
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15f)
        googleMap.animateCamera(cameraUpdate)
       // locationManager.removeUpdates(this)

    }
    //11.02134,76.98354833333333 coimbatore
    protected fun zoomMapInitial(finalPlace: LatLng?, currenLoc: LatLng?) {
        try {
            val padding =
                200 // Space (in px) between bounding box edges and view edges (applied to all four sides of the bounding box)
            val bc = LatLngBounds.Builder()
            bc.include(finalPlace!!)
            bc.include(currenLoc!!)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bc.build(), padding))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    private fun BitmapFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        // below line is use to generate a drawable.
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)

        // below line is use to set bounds to our vector drawable.
        vectorDrawable!!.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )

        // below line is use to create a bitmap for our
        // drawable which we have added.
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )

        // below line is use to add bitmap in our canvas.
        val canvas = Canvas(bitmap)

        // below line is use to draw our
        // vector drawable in canvas.
        vectorDrawable.draw(canvas)

        // after generating our bitmap we are returning our bitmap.
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
    private fun startTimer(){
        object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Used for formatting digit to be in 2 digits only
                val f: NumberFormat = DecimalFormat("00")
                val hour = millisUntilFinished / 3600000 % 24
                val min = millisUntilFinished / 60000 % 60
                val sec = millisUntilFinished / 1000 % 60
                binding.timertext.text =  f.format(sec)
            }

            // When the task is over it will print 00:00:00 there
            override fun onFinish() {
                val animSlideUp: Animation =
                    AnimationUtils.loadAnimation(context, R.anim.slide_down)
                binding.timertext.setText("0")
                binding.orderlayout.animation = animSlideUp
                binding.orderlayout.visibility = View.GONE

                binding.accept.visibility = View.GONE
                binding.reject.visibility = View.GONE
                binding.timerlayout.visibility = View.GONE
            }
        }.start()
    }
}