package `in`.spiegel.driverapp

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

/**
 * Created by Vijay on 12/2/2021.
 */
@ActivityScoped
class SessionManager @Inject constructor(@ApplicationContext context: Context){
    var context: Context = context
    var name = "mysession"
    var sharedPreferences: SharedPreferences
    var editor: SharedPreferences.Editor
    val privatemode = 0
    init {
        sharedPreferences = context.getSharedPreferences(name,privatemode)
        editor = sharedPreferences.edit()
    }

    public fun savevalue(key: String,value:String) {
        editor.putString(key,value)
        editor.commit()
    }
    public fun getvalue(key: String): String? {
        return sharedPreferences.getString(key,"")
    }
    public fun clear(){
        editor.clear()
        editor.commit()
    }
}