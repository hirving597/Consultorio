package com.example.vivito

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val tvGoLoogin = findViewById<TextView>(R.id.tv_go_to_login)
        tvGoLoogin.setOnClickListener {
            goToLogin()
        }

        val btnRegister = findViewById<Button>(R.id.btnRegister)
        btnRegister.setOnClickListener {
            val i = Intent(this, RegisterrActivity::class.java)
            startActivity(i)
        }

    }
    private fun goToLogin(){
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }
    private fun goToMenu(){
        val i = Intent(this, MenuActivity::class.java)
        startActivity(i)
    }
}