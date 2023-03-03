package com.example.shoea

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ProductsListAdapter(private val listener: productItemClicked) : RecyclerView.Adapter<ProductViewHolder>() {

    private val productList = ArrayList<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        val viewHolder = ProductViewHolder(view)
        view.setOnClickListener {
            listener.onProductItemClicked(productList[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentItem = productList[position]
            Glide.with(holder.itemView.context).load(currentItem.thumbnail).into(holder.titleIV)
            holder.titleTV.text = currentItem.title
            holder.brandTV.text = currentItem.brand
            holder.categoryTV.text = currentItem.category
            if(currentItem.stock > 0) holder.stockTV.text = "In Stock"
            else holder.stockTV.text = "Out of Stock"
            holder.priceTV.text = "$"+currentItem.price.toString()+".00"
    }

    // updating the product list
    fun updateProductList(list: ArrayList<Product>){
        productList.clear()
        productList.addAll(list)
        notifyDataSetChanged()
    }
}

class ProductViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    val titleTV: TextView = itemView.findViewById(R.id.idTitle)
    val brandTV: TextView = itemView.findViewById(R.id.idBrand)
    val categoryTV: TextView = itemView.findViewById(R.id.idCategory)
    val stockTV: TextView = itemView.findViewById(R.id.idStock)
    val priceTV: TextView = itemView.findViewById(R.id.idPrice)
    val titleIV: ImageView = itemView.findViewById(R.id.idItemImage)
}

// interface for click handling
interface productItemClicked{
    fun onProductItemClicked(item: Product)
}