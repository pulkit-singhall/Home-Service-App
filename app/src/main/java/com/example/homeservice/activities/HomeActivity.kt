package com.example.homeservice.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homeservice.R
import com.example.homeservice.adapters.BestDealAdapter
import com.example.homeservice.adapters.HomeCategoryAdapter
import com.example.homeservice.databinding.ActivityHomeBinding
import com.example.homeservice.model.BestDealData
import com.example.homeservice.model.CategoryData

class HomeActivity : AppCompatActivity() {
    private lateinit var bind : ActivityHomeBinding
    private lateinit var category_list : ArrayList<CategoryData>
    private lateinit var best_deals_list : ArrayList<BestDealData>

    override fun onCreate(savedInstanceState: Bundle?) {
        bind = ActivityHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(bind.root)

        category_list = ArrayList<CategoryData>()
        best_deals_list = ArrayList<BestDealData>()

        bind.cartPage.setOnClickListener {
            val intent = Intent(this,CartActivity::class.java)
            startActivity(intent)
        }

        bind.profilePage.setOnClickListener {
            val intent = Intent(this,ProfileActivity::class.java)
            startActivity(intent)
        }

        bind.viewAllBtn.setOnClickListener {
            val intent = Intent(this,CategoryActivity::class.java)
            startActivity(intent)
        }

        // CATEGORY RECYCLER VIEW IMPLEMENTATION
        LoadCategory()

        // BEST DEALS RECYCLER VIEW IMPLEMENTATION
        LoadBestDeals()
    }

    private fun LoadCategory() {
        // CATEGORY LIST
        val list = arrayOf("Plumber","Mechanic","Electrician","Cleaner","Cook","Dry Cleaning","Pest Control"
            ,"Carpenter")
        // LOADING DATA INTO CATEGORY LIST
        for(index in list.indices){
            val category = CategoryData(list[index])
            category_list.add(category)
        }
        // SETTING ADAPTER FOR CATEGORY
        bind.categoryRecycler.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        bind.categoryRecycler.setHasFixedSize(true)
        bind.categoryRecycler.adapter = HomeCategoryAdapter(this,category_list)
    }

    private fun LoadBestDeals() {
        // BEST DEALS LIST
        val describe = arrayOf("AC Service","Full House Cleaning","Car Wash","Mobile Repair",
            "Full Time Maid","TV Installation","Tap Fixing","Sofa Dry Cleaning")
        val price = arrayOf("500","1800","300","500","2500","600","300","1500")
        val rating = arrayOf("4.8/5","4.7/5","4.7/5","4.5/5","4.3/5","4.7/5","4.6/5","4.8/5")
        val image = arrayOf(R.drawable.ac_repair,R.drawable.house_clean,R.drawable.carwash,
            R.drawable.mobile,R.drawable.maid,R.drawable.tv, R.drawable.tap,R.drawable.sofa)
        // LOADING DATA INTO BEST DEALS LIST
        for(index in describe.indices){
            val deal = BestDealData(image[index],describe[index],price[index],rating[index])
            best_deals_list.add(deal)
        }
        // SETTING ADAPTER FOR BEST DEALS
        bind.bestDealsRecycler.layoutManager = LinearLayoutManager(this)
        bind.bestDealsRecycler.setHasFixedSize(true)
        bind.bestDealsRecycler.adapter = BestDealAdapter(this,best_deals_list)
    }
}