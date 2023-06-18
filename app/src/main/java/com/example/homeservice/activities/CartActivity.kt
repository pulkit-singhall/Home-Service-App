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
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject

class CartActivity : AppCompatActivity(), PaymentResultListener {
    private lateinit var bind : ActivityCartBinding
    private lateinit var cartList : ArrayList<CartData>
    private lateinit var auth : FirebaseAuth
    private lateinit var fStore : FirebaseFirestore
    private var totalPrice : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        bind = ActivityCartBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(bind.root)

        cartList = ArrayList<CartData>()
        auth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()

        // RAZORPAY LOADING
        Checkout.preload(this)

        bind.checkout.setOnClickListener {
            Toast.makeText(this,"Thanks For Ordering!",Toast.LENGTH_SHORT).show()
            payments()
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

    // RAZORPAY GATEWAY FUNCTION
    private fun payments(){
        val checkout = Checkout()
        checkout.setKeyID("rzp_test_1gUuZEJcItGBMN")
        try {
            val options = JSONObject()
            options.put("name","Payment Gateway")
            options.put("description","Pay Your Amount")
            options.put("theme.color","#3399cc")
            options.put("currency","INR")
            options.put("amount",totalPrice*100)

            // RETRY BLOCK
            val retryObject = JSONObject()
            retryObject.put("enabled",true)
            retryObject.put("max_count",4)
            options.put("retry",retryObject)

            checkout.open(this,options)

        }catch (e : Exception){
            Toast.makeText(this,"Error in Payment!" + e.message,Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(this,"Payment Successful!",Toast.LENGTH_SHORT).show()
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this,"Payment Failure!",Toast.LENGTH_SHORT).show()
    }
}