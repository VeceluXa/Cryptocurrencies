package com.danilovfa.cryptocurrencies.app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.danilovfa.cryptocurrencies.R
import com.danilovfa.cryptocurrencies.databinding.CryptocurrenciesItemBinding
import com.danilovfa.cryptocurrencies.domain.model.CryptocurrencyItem
import com.danilovfa.cryptocurrencies.utils.extension.loadImageByUrlCached
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

class CryptocurrenciesPageAdapter(private val context: Context) :
    RecyclerView.Adapter<CryptocurrenciesPageAdapter.CryptocurrencyViewHolder>() {
    private val layoutInflater = LayoutInflater.from(context)
    private var onItemClickListener: OnItemClickListener? = null

    private val differCallback = object : DiffUtil.ItemCallback<CryptocurrencyItem>() {
        override fun areItemsTheSame(
            oldItem: CryptocurrencyItem,
            newItem: CryptocurrencyItem
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: CryptocurrencyItem,
            newItem: CryptocurrencyItem
        ): Boolean {
            return oldItem.id == newItem.id && oldItem.name == newItem.name
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onBindViewHolder(holder: CryptocurrencyViewHolder, position: Int) {
        val coin = differ.currentList[position]
        holder.bind(coin)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptocurrencyViewHolder {
        val binding = CryptocurrenciesItemBinding.inflate(layoutInflater, parent, false)
        return CryptocurrencyViewHolder(binding)
    }

    override fun getItemCount() = differ.currentList.size

    inner class CryptocurrencyViewHolder(private val binding: CryptocurrenciesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cryptocurrencyItem: CryptocurrencyItem?) {
            cryptocurrencyItem?.let { coin ->
                binding.apply {
                    root.setOnClickListener {
                        onItemClickListener?.onItemClick(
                            coin,
                            binding.coinPrice
                        )
                    }
                    coinName.text = coin.name
                    coinSymbol.text = coin.symbol
                    coinPrice.text = coin.price.toFormattedString()
                    coinPrice.transitionName = coin.id
                    coinMarketCap.text = coin.marketCap.toFormattedString()
                    binding.root.loadImageByUrlCached(
                        imageUrl = coin.imageUrl,
                        container = binding.coinImage,
                        placeholder = R.mipmap.ic_launcher_round
                    )
                }
            }
        }

        private fun Double.toFormattedString(): String {
            val format = NumberFormat.getCurrencyInstance(Locale.US)
            format.currency = Currency.getInstance("USD")
            return format.format(this)
        }
    }

    fun setOnItemClickLister(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(cryptocurrencyItem: CryptocurrencyItem?, priceTextView: TextView)
    }
}