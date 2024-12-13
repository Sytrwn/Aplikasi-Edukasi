package com.example.edukasi

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class BangunRuangActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bangun_ruang)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val imageView = findViewById<ImageView>(R.id.arrow_left)
        imageView.setOnClickListener {
            finish() // Menutup activity saat ini dan kembali ke halaman sebelumnya
        }
        // Button Persegi
        val buttonPersegi = findViewById<Button>(R.id.buttonPersegi)
        buttonPersegi.setOnClickListener {
            val intent = Intent(this, KubusActivity::class.java)
            startActivity(intent)
        }

        // Button Lingkaran
        val buttonLingkaran = findViewById<Button>(R.id.buttonLingkaran)
        buttonLingkaran.setOnClickListener {
            val intent = Intent(this, BalokActivity::class.java)
            startActivity(intent)
        }

        // Button Persegi Panjang
        val buttonPersegiPanjang = findViewById<Button>(R.id.buttonPersegiPanjang)
        buttonPersegiPanjang.setOnClickListener {
            val intent = Intent(this, TabungActivity::class.java)
            startActivity(intent)
        }

        // Button Segitiga
        val buttonSegitiga = findViewById<Button>(R.id.buttonSegitiga)
        buttonSegitiga.setOnClickListener {
            val intent = Intent(this, KerucutActivity::class.java)
            startActivity(intent)

        }
    }

}