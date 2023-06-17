package com.example.homeservice.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.homeservice.R
import com.example.homeservice.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {
    private lateinit var bind : ActivityLoginBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var fStore : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        bind = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(bind.root)

        auth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()

        bind.loginBtn.setOnClickListener{

            // LOGIN USER
            val email = bind.emailLogin.text.toString()
            val password = bind.passwordLogin.text.toString()

            if(validateData(email,password)){
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                    if(it.isSuccessful){

                        Toast.makeText(this,"Login Successful!",Toast.LENGTH_SHORT).show()
                        val intent = Intent(this,HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else{
                        Toast.makeText(this,"Failure!",Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }

        bind.newUser.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun validateData(email: String, password: String): Boolean {
        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this,"Pls Fill The Details!",Toast.LENGTH_SHORT).show()
            return false;
        }
        return true
    }
}