package com.almaz.mukatukha_drinks.ui.cafe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.almaz.mukatukha_drinks.R
import com.almaz.mukatukha_drinks.core.model.Product
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_fragment_cafe_list.view.*
import kotlinx.android.synthetic.main.item_fragment_product_list.view.*

class ProductAdapter(
    private val productLambda: (Product) -> Unit
) : ListAdapter<Product, ProductAdapter.ProductViewHolder>(ProductDiffCallback()) {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(p0.context)
        return ProductViewHolder(inflater.inflate(R.layout.item_fragment_product_list, p0, false))
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            productLambda.invoke(getItem(position))
        }
    }

    class ProductViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bind(product: Product) {
            itemView.tv_product_name.text = product.name
            itemView.tv_volume.text = product.volume
            itemView.tv_price.text = product.price
        }
    }

    class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean = oldItem == newItem

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean = oldItem == newItem
    }
}