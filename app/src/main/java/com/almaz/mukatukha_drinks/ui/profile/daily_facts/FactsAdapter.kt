package com.almaz.mukatukha_drinks.ui.profile.daily_facts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.almaz.mukatukha_drinks.R
import com.almaz.mukatukha_drinks.core.model.Fact
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_daily_fact.view.*

class FactsAdapter : ListAdapter<Fact, FactsAdapter.FactViewHolder>(
    FactDiffCallback()
) {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): FactViewHolder {
        val inflater = LayoutInflater.from(p0.context)
        return FactViewHolder(inflater.inflate(R.layout.item_daily_fact, p0, false))
    }

    override fun onBindViewHolder(holder: FactViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class FactViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bind(fact: Fact) {
            itemView.tv_fact_title.text = fact.title
            itemView.tv_fact_description.text = fact.description
        }
    }

    class FactDiffCallback : DiffUtil.ItemCallback<Fact>() {
        override fun areItemsTheSame(
            oldItem: Fact,
            newItem: Fact
        ): Boolean = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: Fact,
            newItem: Fact
        ): Boolean = oldItem == newItem
    }
}
