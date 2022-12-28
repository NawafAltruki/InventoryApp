package com.example.inventory

import android.content.ClipData
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.inventory.database.inventory.Inventory
import com.example.inventory.database.inventory.getFormattedPrice
import com.example.inventory.databinding.ItemListItemBinding

class ItemListAdapter(private val onItemClicked: (Inventory) -> Unit) :
    ListAdapter<Inventory, ItemListAdapter.ItemViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemListItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }

    class ItemViewHolder(private var binding: ItemListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Inventory) {
            binding.apply {
                itemName.text = item.name
                itemPrice.text = item.getFormattedPrice()
                itemQuantity.text = item.quantity.toString()
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Inventory>() {
            override fun areItemsTheSame(oldItem: Inventory, newItem: Inventory): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Inventory, newItem: Inventory): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }
}