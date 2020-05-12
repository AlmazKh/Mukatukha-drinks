package com.almaz.mukatukha_drinks.ui.basket

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.almaz.mukatukha_drinks.R
import com.almaz.mukatukha_drinks.core.model.Basket
import com.almaz.mukatukha_drinks.core.model.Product
import com.almaz.mukatukha_drinks.core.model.db.BasketAndProduct
import com.almaz.mukatukha_drinks.ui.cafe.EVENT_KEY_ADD
import com.almaz.mukatukha_drinks.ui.cafe.EVENT_KEY_PRODUCT
import com.almaz.mukatukha_drinks.ui.cafe.EVENT_KEY_REMOVE
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_basket_product.view.*

class BasketProductAdapter(
    private val productLambda: (Pair<String, Basket>) -> Unit
) : ListAdapter<Basket, BasketProductAdapter.BasketProductViewHolder>(
    ProductDiffCallback()
) {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): BasketProductViewHolder {
        val inflater = LayoutInflater.from(p0.context)
        return BasketProductViewHolder(inflater.inflate(R.layout.item_basket_product, p0, false))
    }

    override fun onBindViewHolder(holder: BasketProductViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            productLambda.invoke(EVENT_KEY_PRODUCT to getItem(position))
        }
        holder.itemView.btn_add_into_basket.setOnClickListener {
            holder.itemView.tv_product_amount_counter.text =
                (getItem(position).amount + 1).toString()
            productLambda.invoke(EVENT_KEY_ADD to getItem(position))
        }
        holder.itemView.btn_remove_from_basket.setOnClickListener {
            if (holder.itemView.tv_product_amount_counter.text.first() != '0') {
                holder.itemView.tv_product_amount_counter.text =
                    (getItem(position).amount - 1).toString()
                productLambda.invoke(EVENT_KEY_REMOVE to getItem(position))
            }
        }
    }

    class BasketProductViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bind(basket: Basket) {
            itemView.tv_product_name.text = basket.product.name
            itemView.tv_price.text = "${basket.product.price} руб."
            itemView.tv_product_amount_counter.text = basket.amount.toString()
        }
    }

    class ProductDiffCallback : DiffUtil.ItemCallback<Basket>() {
        override fun areItemsTheSame(
            oldItem: Basket,
            newItem: Basket
        ): Boolean = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: Basket,
            newItem: Basket
        ): Boolean = oldItem == newItem
    }

}
