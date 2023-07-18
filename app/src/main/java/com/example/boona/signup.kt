package com.example.boona

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class signup : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        supportActionBar?.hide()

        auth= FirebaseAuth.getInstance()
        val button= findViewById<Button>(R.id.signupbutton)
        val backlogin1=findViewById<Button>(R.id.backlogin)

        backlogin1.setOnClickListener { startActivity(Intent(this, login::class.java)) }

        button.setOnClickListener{
            //  startActivity(Intent( this, MainActivity::class.java))
            val email1= findViewById<TextInputEditText>(R.id.email1)
            val password1= findViewById<TextInputEditText>(R.id.password1)

            val email= email1.text.toString()
            val password =password1.text.toString()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        FirebaseAuth.getInstance().currentUser?.sendEmailVerification()
                        FirebaseAuth.getInstance().signOut()
                        startActivity(Intent(this, login::class.java))
                        Toast.makeText(
                            baseContext,
                            "Authentication successful.",
                            Toast.LENGTH_SHORT,
                        ).show()


                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "weak password or invalid credentials.",
                            Toast.LENGTH_SHORT,
                        ).show()

                    }
    }
}}}