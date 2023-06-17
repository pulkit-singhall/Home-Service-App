package com.example.homeservice.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.homeservice.R
import com.example.homeservice.model.CartData

class CartAdapter(val context: Activity, val cartList : ArrayList<CartData>) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_each_item,parent,false)
        return CartAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartAdapter.ViewHolder, position: Int) {
        val currentItem = cartList[position]
        holder.describe.text = currentItem.description
        holder.price.text = "Rs. " + currentItem.price
        holder.rating.text = currentItem.rating
        holder.date.text = currentItem.date
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val describe = view.findViewById<TextView>(R.id.description)
        val price = view.findViewById<TextView>(R.id.price)
        val rating = view.findViewById<TextView>(R.id.rating)
        val date = view.findViewById<TextView>(R.id.date_slot)
    }
}