package com.example.homeservice.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.homeservice.R
import com.example.homeservice.model.CategoryData
import java.util.Locale.Category

class HomeCategoryAdapter(val context: Activity, val categoryList : ArrayList<CategoryData>) :
    RecyclerView.Adapter<HomeCategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeCategoryAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_home_page_each_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeCategoryAdapter.ViewHolder, position: Int) {
        val current_category = categoryList[position]
        holder.category_name.text = current_category.name
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val category_name = view.findViewById<TextView>(R.id.category_name)
    }
}