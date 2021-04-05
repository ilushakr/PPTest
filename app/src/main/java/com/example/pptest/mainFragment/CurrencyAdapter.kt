package com.example.pptest.mainFragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pptest.R
import com.example.pptest.entities.Valute
import kotlinx.android.synthetic.main.currency_card.view.*

class CurrencyAdapter(
    private val data: List<Valute>
): RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>(){

    class CurrencyViewHolder(view: View, private val context: Context) : RecyclerView.ViewHolder(view){
        private val currencyName: TextView = view.currencyName
        private val currencyIcon: TextView = view.currencyIcon


        fun initViews(valute: Valute){
            currencyName.text = valute.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.currency_card, parent, false)

        return CurrencyViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.initViews(data[position])
    }

    override fun getItemCount() = data.size
}