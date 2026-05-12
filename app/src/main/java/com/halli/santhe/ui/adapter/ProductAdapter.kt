package com.halli.santhe.ui.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.halli.santhe.R
import com.halli.santhe.data.Product
import com.halli.santhe.databinding.ItemProductBinding

class ProductAdapter(private val onClick: (Product) -> Unit) :
    ListAdapter<Product, ProductAdapter.VH>(DIFF) {

    inner class VH(val b: ItemProductBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(p: Product) {
            b.tvName.text = p.name
            b.tvPrice.text = b.root.context.getString(R.string.price_format, p.price)
            b.tvCategory.text = p.category
            if (!p.imageUri.isNullOrEmpty()) {
                Glide.with(b.ivImage).load(Uri.parse(p.imageUri))
                    .placeholder(R.drawable.ic_placeholder).into(b.ivImage)
            } else b.ivImage.setImageResource(R.drawable.ic_placeholder)
            b.root.setOnClickListener { onClick(p) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(getItem(position))

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(a: Product, b: Product) = a.id == b.id
            override fun areContentsTheSame(a: Product, b: Product) = a == b
        }
    }
}
