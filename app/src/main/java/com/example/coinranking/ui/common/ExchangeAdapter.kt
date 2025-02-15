package com.example.coinranking.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.coinranking.data.model.Exchange
import com.example.coinranking.databinding.ItemExchangeBinding

class ExchangeAdapter(
    private val onItemClickListener: (Exchange) -> Unit
) : ListAdapter<Exchange, RecyclerView.ViewHolder>(
    object : DiffUtil.ItemCallback<Exchange>() {
        override fun areItemsTheSame(oldItem: Exchange, newItem: Exchange): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Exchange, newItem: Exchange): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
) {

    inner class LinearViewHolder(
        private val binding: ItemExchangeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClickListener(getItem(adapterPosition))
            }
        }

        fun bind(item: Exchange) {
            binding.exchange = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val binding = ItemExchangeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return LinearViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LinearViewHolder -> {
                holder.bind(getItem(position))
            }
        }
    }
}