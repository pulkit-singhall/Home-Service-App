package com.example.homeservice.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.homeservice.R
import com.example.homeservice.model.CategoryData
import com.example.homeservice.model.CategoryFullData

class CategoryAdapter(val context : Activity, var categoryList : ArrayList<CategoryFullData>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_page_layout_each_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {
        val current_category = categoryList[position]
        holder.category_name.text = current_category.name
        holder.image_name.setImageResource(current_category.image)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val category_name = view.findViewById<TextView>(R.id.category_name)
        val image_name = view.findViewById<ImageView>(R.id.image_name)
    }

}