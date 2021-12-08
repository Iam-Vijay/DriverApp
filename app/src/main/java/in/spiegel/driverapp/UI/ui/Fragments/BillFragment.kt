package `in`.spiegel.driverapp.UI.ui.Fragments

import `in`.spiegel.driverapp.BaseFragment
import `in`.spiegel.driverapp.R
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class BillFragment : BaseFragment() {

    companion object {
        fun newInstance() = BillFragment()
    }

    private lateinit var viewModel: BillViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bill_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BillViewModel::class.java)
        // TODO: Use the ViewModel
    }

}