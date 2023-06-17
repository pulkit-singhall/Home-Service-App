package com.example.homeservice.activities

import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.homeservice.R
import com.example.homeservice.databinding.ActivityProfileBinding
import com.example.homeservice.model.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class ProfileActivity : AppCompatActivity() {
    private lateinit var bind : ActivityProfileBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var fStore : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        bind = ActivityProfileBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(bind.root)

        auth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()

        val currentUser = auth.currentUser
        val uid = currentUser?.uid!!

        // DISPLAY USER FROM DATABASE (NAME AND EMAIL)

        fStore.collection("Users").document(uid).collection("Details").get().addOnSuccessListener {
            if(it.isEmpty == false){
                for(data in it.documents){
                    val name = data.getString("name").toString()
                    val email = data.getString("email").toString()
                    bind.userName.text = name
                    bind.userEmail.text = email
                }
            }
        }

        bind.backBtn.setOnClickListener {
            val intent = Intent(this,HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        bind.signOutBtn.setOnClickListener {
            // SIGN OUT USER
            Firebase.auth.signOut()
            Toast.makeText(this,"User Signed Out!",Toast.LENGTH_SHORT).show()
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}