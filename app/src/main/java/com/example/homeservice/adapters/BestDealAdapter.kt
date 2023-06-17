package com.example.homeservice.adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.homeservice.R
import com.example.homeservice.activities.DetailActivity
import com.example.homeservice.model.BestDealData

class BestDealAdapter(val context: Activity, val best_deals_list : ArrayList<BestDealData>)
    : RecyclerView.Adapter<BestDealAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestDealAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.best_deals_layout_recyclerview,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: BestDealAdapter.ViewHolder, position: Int) {
        val current_deal = best_deals_list[position]
        holder.describe.text = current_deal.description
        holder.price.text = "Rs. " + current_deal.price
        holder.rating.text = current_deal.rating
        holder.image.setImageResource(current_deal.image)

        holder.itemView.setOnClickListener {
            val intent = Intent(context,DetailActivity::class.java)
            intent.putExtra("description",current_deal.description)
            intent.putExtra("price",current_deal.price)
            intent.putExtra("rating",current_deal.rating)
            intent.putExtra("image",current_deal.image)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return best_deals_list.size
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val image = view.findViewById<ImageView>(R.id.image)
        val describe = view.findViewById<TextView>(R.id.description)
        val price = view.findViewById<TextView>(R.id.price)
        val rating = view.findViewById<TextView>(R.id.rating)
    }
}