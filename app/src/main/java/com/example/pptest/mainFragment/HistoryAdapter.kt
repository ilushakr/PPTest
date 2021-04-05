package com.example.pptest.mainFragment

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pptest.R
import com.example.pptest.databinding.HistoryCardBinding
import com.example.pptest.entities.TransactionHistory
import com.example.pptest.utils.Utils.addChar

class HistoryAdapter: RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>(){

    private val data: MutableList<TransactionHistory> = mutableListOf()

    fun setData(data: List<TransactionHistory>){
        this.data.clear()
        this.data.addAll(data)
        this.notifyDataSetChanged()
    }

    class HistoryViewHolder(private val binding: HistoryCardBinding, private val context: Context) : RecyclerView.ViewHolder(binding.root) {

        fun bind(transactionHistory: TransactionHistory) {
            transactionHistory.balanceUsdSigned = addChar(transactionHistory.balanceUsd, " $ ")
            binding.transaction = transactionHistory
            Glide
                .with(context)
                .load(transactionHistory.iconUrl)
                .into(binding.historyIcon)
            binding.executePendingBindings()
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding: HistoryCardBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.history_card,
            parent,
            false
        )
        return HistoryViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size
}