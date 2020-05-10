package com.almaz.mukatukha_drinks.ui.special_offer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.almaz.mukatukha_drinks.R
import com.almaz.mukatukha_drinks.core.model.Offer
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_fragment_offers_list.view.*

class OffersAdapter : ListAdapter<Offer, OffersAdapter.OfferViewHolder>(OfferDiffCallback()) {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): OfferViewHolder {
        val inflater = LayoutInflater.from(p0.context)
        return OfferViewHolder(inflater.inflate(R.layout.item_fragment_offers_list, p0, false))
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class OfferViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bind(offer: Offer) {
            itemView.tv_cafe_name.text = offer.cafe.name
            itemView.tv_cafe_address.text = offer.cafe.address
            itemView.tv_discount_description.text = offer.offerDescription
        }
    }

    class OfferDiffCallback : DiffUtil.ItemCallback<Offer>() {
        override fun areItemsTheSame(oldItem: Offer, newItem: Offer): Boolean = oldItem == newItem

        override fun areContentsTheSame(oldItem: Offer, newItem: Offer): Boolean =
            oldItem == newItem
    }
}
