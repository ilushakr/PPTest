package com.example.pptest.cards

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pptest.R
import com.example.pptest.entities.UserDB
import kotlinx.android.synthetic.main.card_item.view.*

class CardsAdapter(private val data: List<UserDB>):
    RecyclerView.Adapter<CardsAdapter.CardsAdapterViewHolder>(){

    inner class CardsAdapterViewHolder(private val view: View) : RecyclerView.ViewHolder(view){
        private val cardNumberItem: TextView = view.cardNumberItem
        private val icon: ImageView = view.typeIcon
        private val checker: ImageView = view.checker

        fun initViews(userDB: UserDB){
           cardNumberItem.text = userDB.cardNumber
            if(currentCard == userDB.cardNumber){
                checker.setColorFilter(R.color.blue)
            }

            when(userDB.type){
                "mastercard" -> icon.setImageResource(R.drawable.ic_type_mastercard)
                "visa" -> icon.setImageResource(R.drawable.ic_group_visa)
                "unionpay" -> icon.setImageResource(R.drawable.ic_group_unionpay)
            }

            view.setOnClickListener {
                listener.setCard(userDB.cardNumber)
            }
        }
    }

    private lateinit var listener: AdapterListener
    private lateinit var currentCard: String

    fun setListener(listener: AdapterListener){
        this.listener = listener
    }

    fun setCurrentCard(currentCard: String){
        this.currentCard = currentCard
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardsAdapterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_item, parent, false)

        return CardsAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardsAdapterViewHolder, position: Int) {
        holder.initViews(data[position])
    }

    override fun getItemCount() = data.size

    interface AdapterListener{
        fun setCard(card: String)
    }
}