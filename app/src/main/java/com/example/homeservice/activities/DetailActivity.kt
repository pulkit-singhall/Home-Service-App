package com.example.homeservice.activities

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.homeservice.databinding.ActivityDetailBinding
import com.example.homeservice.model.CartData
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialDatePicker.Builder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class DetailActivity : AppCompatActivity() {
    private lateinit var bind : ActivityDetailBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var fStore : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        bind = ActivityDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(bind.root)

        auth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()

        val currentUser = auth.currentUser
        val uid = currentUser?.uid

        // FOR DATE PICKER
        val calender = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, day ->
            calender.set(Calendar.YEAR,year)
            calender.set(Calendar.MONTH,month)
            calender.set(Calendar.DAY_OF_MONTH,day)
            updateCalender(calender)
        }

        val description = intent.getStringExtra("description")
        val price = intent.getStringExtra("price")
        val rating  = intent.getStringExtra("rating")
        val image = intent.getIntExtra("image",1)

        // PASSING DATA INTO DETAIL ACTIVITY
        bind.description.text = description
        bind.rating.text = rating
        bind.price.text = "Rs. " + price
        bind.image.setImageResource(image)

        bind.addToCartBtn.setOnClickListener {
            // ADD DATA TO DATABASE (CART)

            val description = bind.description.text.toString()
            val rating = bind.rating.text.toString()
            val price = intent.getStringExtra("price").toString()
            val date = bind.dateSlot.text.toString()

            val Item = CartData(description,price,rating,date)

            uid?.let { it1 -> fStore.collection("Cart").document(it1).collection("Items").document()
                .set(Item).addOnCompleteListener {
                    if(it.isSuccessful){
                        Toast.makeText(this,"Added To Cart!",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        // SETTING TIME SLOTS
        bind.slotBtn.setOnClickListener {
            DatePickerDialog(this,datePicker,calender.get(Calendar.YEAR),calender.get(Calendar.MONTH)
            ,calender.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    private fun updateCalender(calender: Calendar) {
        val dateFormat = SimpleDateFormat("dd MM yyyy", Locale.getDefault())
        val date = dateFormat.format(calender.time)
        bind.dateSlot.text = date
    }
}