package com.almaz.mukatukha_drinks.ui.cafe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.almaz.mukatukha_drinks.R
import com.almaz.mukatukha_drinks.core.model.Cafe
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_fragment_cafe_list.view.*
import java.util.*

class CafeAdapter (
    private val cabinetLambda: (Cafe) -> Unit
) : ListAdapter<Cafe, CafeAdapter.CafeViewHolder>(CafeDiffCallback()), Filterable {

    private var filteredList: MutableList<Cafe> = mutableListOf()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CafeViewHolder {
        val inflater = LayoutInflater.from(p0.context)
        return CafeViewHolder(inflater.inflate(R.layout.item_fragment_cafe_list, p0, false))
    }

    override fun onBindViewHolder(holder: CafeViewHolder, position: Int) {
        holder.bind(filteredList[position])
        holder.itemView.setOnClickListener {
            cabinetLambda.invoke(filteredList[position])
        }
    }

    override fun submitList(list: MutableList<Cafe>?) {
        super.submitList(list)
        filteredList = list!!
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    class CafeViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bind(cafe: Cafe) {
            itemView.tv_cafe_name.text = cafe.name
            itemView.tv_address.text = cafe.address
            itemView.tv_average_cooking_time.text = cafe.averageCookingTime
        }
    }

    class CafeDiffCallback : DiffUtil.ItemCallback<Cafe>() {
        override fun areItemsTheSame(oldItem: Cafe, newItem: Cafe): Boolean = oldItem == newItem

        override fun areContentsTheSame(oldItem: Cafe, newItem: Cafe): Boolean = oldItem == newItem
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    filteredList = currentList
                } else {
                    val resultList: MutableList<Cafe> = mutableListOf()
                    for (cafe in currentList) {
                        if (cafe.name.toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT))
                        ) {
                            resultList.add(cafe)
                        }
                    }
                    filteredList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as MutableList<Cafe>
                notifyDataSetChanged()
            }
        }
    }
}