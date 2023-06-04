package com.training.eshopper.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.squareup.picasso.Picasso
import com.training.eshopper.R
import com.training.eshopper.common.Constant
import com.training.eshopper.data.model.Product
import com.training.eshopper.databinding.ProductItemBinding
import org.json.JSONArray

class ProductAdapter :
    ListAdapter<Product, ProductAdapter.MyViewHolder>(ProductDiffCallback()) {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ProductItemBinding.bind(itemView)
    }

    class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {

        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val product = getItem(position)
        val images = JSONArray(product.image)
        holder.binding.apply {
            Picasso.get().load(Constant.BASE_PRODUCT_IMAGE_URL + product.idUser + "/" + images[0]).into(image)
            title.text = product.name
            price.text = product.price.toString() + "$"
        }
    }
}