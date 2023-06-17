package com.example.homeservice.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homeservice.adapters.CartAdapter
import com.example.homeservice.databinding.ActivityCartBinding
import com.example.homeservice.model.CartData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CartActivity : AppCompatActivity() {
    private lateinit var bind : ActivityCartBinding
    private lateinit var cartList : ArrayList<CartData>
    private lateinit var auth : FirebaseAuth
    private lateinit var fStore : FirebaseFirestore
    private var totalPrice = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        bind = ActivityCartBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(bind.root)

        cartList = ArrayList<CartData>()
        auth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()

        bind.checkout.setOnClickListener {
            Toast.makeText(this,"Thanks For Ordering!",Toast.LENGTH_SHORT).show()
        }

        // CART DATA LOADING
        loadCartData()

        // SETTING UP ADAPTER
        bind.cartRecyclerView.layoutManager = LinearLayoutManager(this)
        bind.cartRecyclerView.setHasFixedSize(true)
    }

    private fun loadCartData() {
        // LOAD DATA FROM DATABASE
        val currentUser = auth.currentUser
        val uid = currentUser?.uid!!

        cartList.clear()
        fStore.collection("Cart").document(uid).collection("Items").get()
            .addOnSuccessListener{
                if(it.isEmpty == false){
                    for(data in it.documents){
                        val description = data.getString("description")!!
                        val price = data.getString("price")!!
                        val rating = data.getString("rating")!!
                        val date = data.getString("date")!!

                        // FOR TOTAL PRICE
                        val priceInt = price.toInt()
                        totalPrice = totalPrice + priceInt

                        val CartItem = CartData(description,price,rating,date)
                        if(CartItem != null){
                            cartList.add(CartItem)
                        }
                    }
                    // ADAPTER HERE
                    bind.cartRecyclerView.adapter = CartAdapter(this,cartList)
                    bind.totalPrice.text = "Rs. " + totalPrice.toString()
                }
            }
    }
}