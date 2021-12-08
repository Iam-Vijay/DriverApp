package `in`.spiegel.driverapp.Repository

import `in`.spiegel.driverapp.Api.ApiInterface
import `in`.spiegel.driverapp.Api.ApiRequest
import `in`.spiegel.driverapp.Model.MainModel
import javax.inject.Inject

/**
 * Created by Vijay on 12/2/2021.
 */
class ActivityMainRepository @Inject constructor(private val apiInterface: ApiInterface):ApiRequest() {

    suspend fun Signup(name:String,email:String,mobile:String,taxino:String,taximodel:String):MainModel{
        return apirequest { apiInterface.Register(name,email,mobile, taxino, taximodel).await() }
    }

}