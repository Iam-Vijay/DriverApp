package `in`.spiegel.driverapp.UI.ui.Fragments

import `in`.spiegel.driverapp.BaseFragment
import `in`.spiegel.driverapp.Model.HistoryModel
import `in`.spiegel.driverapp.R
import `in`.spiegel.driverapp.adapters.HistoryAdapter
import `in`.spiegel.driverapp.databinding.HistoryFragmentBinding
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HistoryFragment : BaseFragment() {

    companion object {
        fun newInstance() = HistoryFragment()
    }
    @Inject
    lateinit var adapterr:HistoryAdapter

    private lateinit var viewModel: HistoryViewModel
    lateinit var binding: HistoryFragmentBinding
    var array = ArrayList<HistoryModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =  HistoryFragmentBinding.inflate(inflater, container, false)

        binding.historyrecylerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterr
        }
        array.apply {
            val item1 = HistoryModel("madurai","coimbatore","205","1500/-")
            add(item1)
            val item2 = HistoryModel("coimbatore","madurai","205","1500/-")
            add(item2)
            val item3 = HistoryModel("madurai","chennai","495","3500/-")
            add(item3)
            val item4 = HistoryModel("chennai","delhi","3500","25000/-")
            add(item4)
            val item5 = HistoryModel("delhi","Madurai","4000","30000/-")
            add(item5)
            adapterr.updateList(array)
        }
        return binding.root
    }


}