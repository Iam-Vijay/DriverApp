package `in`.spiegel.driverapp.Model

import com.google.gson.annotations.SerializedName

/**
 * Created by Vijay on 12/2/2021.
 */

data class MainModel(@field:SerializedName("status")var status:String? = null,
                     @field:SerializedName("msg") var msg:String? = null)
