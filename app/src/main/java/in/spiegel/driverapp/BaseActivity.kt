package `in`.spiegel.driverapp

import android.app.Activity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Vijay on 12/2/2021.
 */
@AndroidEntryPoint
abstract class BaseActivity: AppCompatActivity() {

    fun validateEdittext(editText: EditText,tag :String):Boolean{
        return if (editText.text.trim().isEmpty()){
            editText.error = tag
            requestFocus(editText)
            false
        }else{
            editText.error = null
            true
        }

    }

    fun requestFocus(view: View){
        if (view.requestFocus())
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
    }

}