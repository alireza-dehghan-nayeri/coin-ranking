package com.example.coinranking.ui.common


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.coinranking.R
import com.example.coinranking.data.model.CoinAndBookmark
import com.example.coinranking.databinding.ItemCoinBinding

class CoinAdapter(
    private val onItemClickListener: (CoinAndBookmark) -> Unit,
    private val onBookmarkItemClickListener: (CoinAndBookmark, Int) -> Unit,
    private val isCoinBookmarked: (CoinAndBookmark) -> Boolean
) : ListAdapter<CoinAndBookmark, RecyclerView.ViewHolder>(
    object : DiffUtil.ItemCallback<CoinAndBookmark>() {
        override fun areItemsTheSame(oldItem: CoinAndBookmark, newItem: CoinAndBookmark): Boolean {
            return oldItem.coin.name == newItem.coin.name
        }

        override fun areContentsTheSame(
            oldItem: CoinAndBookmark,
            newItem: CoinAndBookmark
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
) {

    inner class LinearViewHolder(
        private val binding: ItemCoinBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClickListener(getItem(adapterPosition))
            }

            binding.ivBookmark.setOnClickListener {
                onBookmarkItemClickListener(getItem(adapterPosition), adapterPosition)
            }

        }

        fun bind(item: CoinAndBookmark) {
            binding.coin = item.coin
            if (isCoinBookmarked(item)) {
                binding.ivBookmark.setImageResource(R.drawable.ic_bookmark_filled)
            } else {
                binding.ivBookmark.setImageResource(R.drawable.ic_bookmark_empty)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val binding = ItemCoinBinding.inflate(
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