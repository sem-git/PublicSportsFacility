package ddwu.com.mobileapp.publicsportsfacility

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ddwu.com.mobileapp.publicsportsfacility.databinding.ListItemBinding

class FacilityAdapter : RecyclerView.Adapter<FacilityAdapter.FacilityHolder>() {

    var facilities: List<Facility>? = null

    override  fun getItemCount(): Int {
        return facilities?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacilityHolder {
        val itemBinding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return FacilityHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: FacilityHolder, position: Int) {
        val facility = facilities?.get(position)
        facility?.let {
            holder.itemBinding.tvFacilityName.text = it.SVCNM
            holder.itemBinding.tvFacilityLocation.text = it.PLACENM
            holder.itemBinding.tvFacilityStatus.text = it.SVCSTATNM
        }
    }

    class FacilityHolder(val itemBinding: ListItemBinding) : RecyclerView.ViewHolder(itemBinding.root)

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    var clickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.clickListener = listener
    }
}