package `in`.spiegel.driverapp.Api

import org.json.JSONObject
import retrofit2.Response
import java.io.IOException
import java.lang.StringBuilder

/**
 * Created by Vijay on 12/2/2021.
 */
abstract class ApiRequest {
    suspend fun<T:Any> apirequest(call : suspend() ->Response<T>):T{
            val response = call.invoke()

        if (response.isSuccessful){
            return response.body()!!
        }else{
            val errormsg = response.errorBody().toString()
            val message = StringBuilder()

            message.append(JSONObject(errormsg).getString("message"))
            message.append(response.code().toString())
        throw ApiEXepction(message.toString())
        }
    }

     class ApiEXepction(toString: String): IOException(toString)
}