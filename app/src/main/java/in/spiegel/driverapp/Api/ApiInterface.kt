package `in`.spiegel.driverapp.Api

import `in`.spiegel.driverapp.Model.MainModel
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by Vijay on 12/2/2021.
 */
interface ApiInterface {
 @FormUrlEncoded
 @POST
 fun Register(@Field("name") name:String,
              @Field("email")email:String,
              @Field("mobile")mobile:String,
              @Field("taxino")taxino:String,
              @Field("taximodel")taximodel:String):Deferred<Response<MainModel>>

 @FormUrlEncoded
 @POST
 fun UpdateLocation(@Field("lat")lat:String,@Field("lng")lng:String,@Field("driverid")driverid:String):Deferred<Response<MainModel>>

}