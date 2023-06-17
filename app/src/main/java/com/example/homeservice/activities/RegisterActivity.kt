package com.example.homeservice.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.homeservice.R
import com.example.homeservice.databinding.ActivityRegisterBinding
import com.example.homeservice.model.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {
    private lateinit var bind : ActivityRegisterBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var fStore : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        bind = ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(bind.root)

        auth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()

        bind.registerBtn.setOnClickListener {

            // REGISTER USER
            val name = bind.nameRegister.text.toString()
            val email = bind.emailRegister.text.toString()
            val password = bind.passwordRegister.text.toString()

            if(validateData(name,email,password)){
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if(it.isSuccessful){

                        val currentUser = auth.currentUser
                        val uid = currentUser?.uid!!

                        val userToAdd = UserData(name,email,uid)

                        // LOAD USER IN DATABASE
                        fStore.collection("Users").document(uid).collection("Details").document()
                            .set(userToAdd).addOnCompleteListener {
                            if(it.isSuccessful){
                                Toast.makeText(this,"Registration Successful!", Toast.LENGTH_SHORT).show()
                            }
                        }

                        val intent = Intent(this,LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else{
                        Toast.makeText(this,"Failure!", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }

    private fun validateData(name: String, email: String, password: String): Boolean {
        if(email.isEmpty() || password.isEmpty() || name.isEmpty()){
            Toast.makeText(this,"Pls Fill The Details!", Toast.LENGTH_SHORT).show()
            return false;
        }
        return true
    }

}