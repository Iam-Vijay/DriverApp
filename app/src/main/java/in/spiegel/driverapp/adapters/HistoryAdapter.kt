package `in`.spiegel.driverapp.adapters

import `in`.spiegel.driverapp.Model.HistoryModel
import `in`.spiegel.driverapp.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Created by Vijay on 12/8/2021.
 */
class HistoryAdapter @Inject constructor(@ApplicationContext var context: Context) : RecyclerView.Adapter<HistoryAdapter.MyviewHolder>() {
    private var arrayList:ArrayList<HistoryModel> = ArrayList()
    var clicklistners:((pos:Int,item:String) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyviewHolder {
        val layoutinflater = LayoutInflater.from(context)
        val item:View = layoutinflater.inflate(R.layout.historyitem,parent,false)
        return MyviewHolder(item)
    }

    override fun onBindViewHolder(holder: MyviewHolder, position: Int) {
        val listitem = arrayList[position]
        holder.pickup.text = listitem.pickup
        holder.drop.text = listitem.drop
        holder.distance.text = listitem.distance
        holder.fare.text = listitem.fare
    }

    override fun getItemCount(): Int {
       return arrayList.size
    }
fun updateList( array: List<HistoryModel>){
    this.arrayList.apply {
        clear()
        addAll(array)
        notifyDataSetChanged()
    }
}
    class MyviewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pickup:TextView = itemView.findViewById(R.id.pickup)
        var drop:TextView = itemView.findViewById(R.id.drop)
        var fare:TextView = itemView.findViewById(R.id.fare)
        var distance:TextView = itemView.findViewById(R.id.distance)

    }
}


