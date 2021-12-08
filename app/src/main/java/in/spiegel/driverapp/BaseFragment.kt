package `in`.spiegel.driverapp

import android.view.View
import android.view.WindowManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Vijay on 12/8/2021.
 */
@AndroidEntryPoint
abstract class BaseFragment :Fragment() {
    fun validateEdittext(editText: EditText, tag :String):Boolean{
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
            requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
    }
}