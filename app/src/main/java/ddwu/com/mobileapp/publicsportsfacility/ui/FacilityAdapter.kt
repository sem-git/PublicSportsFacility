package ddwu.com.mobileapp.publicsportsfacility.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ddwu.com.mobileapp.publicsportsfacility.data.network.Facility
import ddwu.com.mobileapp.publicsportsfacility.databinding.ListItemBinding

class FacilityAdapter : RecyclerView.Adapter<FacilityAdapter.FacilityHolder>() {
    var facilities: List<Facility>? = null

    override fun getItemCount(): Int {
        return facilities?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacilityHolder {
        val itemBinding =
            ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FacilityHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: FacilityHolder, position: Int) {
        val facility = facilities?.get(position)
        facility?.let {
            holder.itemBinding.tvSubClass.text = it.MINCLASSNM
            holder.itemBinding.tvServiceStatus.text = it.SVCSTATNM
            holder.itemBinding.tvPlaceName.text = it.PLACENM
            holder.itemBinding.tvServiceName.text = it.SVCNM
            holder.itemBinding.tvAreaName.text = it.AREANM
            holder.itemBinding.tvHours.text = "${it.V_MIN} - ${it.V_MAX}"

            Glide.with(holder.itemBinding.root.context)
                .load(it.IMGURL)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_dialog_alert)
                .into(holder.itemBinding.ivPlace)
        }
    }

    class FacilityHolder(val itemBinding: ListItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    var clickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.clickListener = listener
    }
}