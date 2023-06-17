package com.example.homeservice.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homeservice.R
import com.example.homeservice.adapters.CategoryAdapter
import com.example.homeservice.databinding.ActivityCategoryBinding
import com.example.homeservice.model.CategoryFullData
import java.util.*
import kotlin.collections.ArrayList

class CategoryActivity : AppCompatActivity() {
    private lateinit var bind : ActivityCategoryBinding
    private lateinit var categoryList : ArrayList<CategoryFullData>
    private lateinit var adapter : CategoryAdapter
    private lateinit var filteredList : ArrayList<CategoryFullData>

    override fun onCreate(savedInstanceState: Bundle?) {
        bind = ActivityCategoryBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(bind.root)

        categoryList = ArrayList<CategoryFullData>()
        filteredList = ArrayList<CategoryFullData>()

        // CATEGORY LIST
        val list = arrayOf("Plumber","Mechanic","Electrician","Cleaner","Cook","Barber","Gardener","AC Service",
            "Grooming","Spa","Facial","Pest Control","Carpenter","Health Care","Technician","Device Installation",
             "Painter","Furniture Repair","Water Proofing","Dry Cleaning","Tailoring","Scrapper")

        // IMAGE LIST
        val images = arrayOf(R.drawable.plumbering,R.drawable.mechanic,R.drawable.electrician,R.drawable.cleaning,
            R.drawable.cooking,R.drawable.barber,R.drawable.gardener,R.drawable.air_conditioner,
            R.drawable.grooming,R.drawable.massage,R.drawable.grooming,R.drawable.insecticide,
            R.drawable.carpenter,R.drawable.healthcare,R.drawable.technician,R.drawable.install,
            R.drawable.painter,R.drawable.home_repair,R.drawable.water_proof,R.drawable.dry,
        R.drawable.sewing,R.drawable.scrap)

//         LOADING DATA INTO CATEGORY LIST
        for(index in list.indices){
            val category = CategoryFullData(list[index],images[index])
            categoryList.add(category)
        }

        // IMPORTANT
        filteredList.addAll(categoryList)

        // PASSING FILTERED LIST
        adapter = CategoryAdapter(this,filteredList)

        // ADAPTER SETTING
        bind.categoryRecycle.layoutManager = LinearLayoutManager(this)
        bind.categoryRecycle.setHasFixedSize(true)

        bind.categoryRecycle.adapter = adapter


        // SEARCH VIEW IMPLEMENTATION
        bind.searchCategory.clearFocus()

        bind.searchCategory.setOnQueryTextListener(object: SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                filteredList.clear()
                val searchText = query!!.lowercase(Locale.getDefault())

                if(searchText.isNotEmpty()){
                    for(index in categoryList){
                        if(index.name.lowercase(Locale.getDefault()).contains(searchText)){
                            filteredList.add(index)
                        }
                    }
                    adapter.notifyDataSetChanged()
                }
                else{
                    filteredList.clear()
                    filteredList.addAll(categoryList)
                    adapter.notifyDataSetChanged()
                }

                return false
            }

        })
    }

}