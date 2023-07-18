package com.example.boona

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class login : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        auth= FirebaseAuth.getInstance()

        val button = findViewById<Button>(R.id.loginbutton)
        val email1 = findViewById<TextInputEditText>(R.id.emaillogin)
        val password1= findViewById<TextInputEditText>(R.id.password)
        val signinTV = findViewById<TextView>( R.id.signinTV)
        val already=findViewById<TextView>(R.id.already)
        val progress=findViewById<ProgressBar>(R.id.progressbar)


        button.setOnClickListener {
            progress.visibility= View.VISIBLE
            // This step will take trake us to the main activity if the login details are all succeful
            val email = email1.text.toString()
            val password = password1.text.toString()
            //adding another if statement to make sure that the application does not crush
            if (email.isNotEmpty() || password.isNotEmpty()) {
                //connecting to the fire base to ensure that the information is valid(not null)
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            if (FirebaseAuth.getInstance().currentUser?.isEmailVerified == true) {
                                startActivity(Intent(this, MainActivity::class.java))
                                Toast.makeText(
                                    baseContext,
                                    "Your login Has been Successful",
                                    Toast.LENGTH_LONG,
                                ).show()

                            } else {
                                Snackbar.make(
                                    it,
                                    "Login failed! Did you verify with your email?",
                                    Snackbar.LENGTH_LONG
                                ).show()
                            }

                        } else {
                            //if the login fails display the following information
                            Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext,
                                "Invalid Credentials",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            } else{
                Toast.makeText(
                    baseContext,
                    "PLEASE USE YOUR CREDENTIALS TO LOGIN",
                    Toast.LENGTH_LONG
                ).show()
            }


        }

        signinTV.setOnClickListener {
            startActivity(  Intent(this, signup::class.java))
        }

        already.setOnClickListener {
            startActivity(Intent(this, login::class.java))
        }

    }
}