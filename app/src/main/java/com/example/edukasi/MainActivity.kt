package com.example.edukasi

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Handle WindowInsets (optional)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Tambahkan listener pada TextView
        val textView = findViewById<TextView>(R.id.textViewHelloKotlin)
        textView.setOnClickListener {
            // Pindah ke PersegiActivity
            val intent = Intent(this, BangunDatarActivity::class.java)
            startActivity(intent)
        }

        val bangunDatarImageView = findViewById<ImageView>(R.id.bangunDatar)
        bangunDatarImageView.setOnClickListener {
            // Pindah ke PersegiActivity
            val intent = Intent(this, BangunDatarActivity::class.java)
            startActivity(intent)
        }

        //Page untuk ke bangun ruang
        val imageView = findViewById<ImageView>(R.id.bangunRuang)
        imageView.setOnClickListener {

            val intent = Intent(this, BangunRuangActivity::class.java)
            startActivity(intent)
        }
    }
}
