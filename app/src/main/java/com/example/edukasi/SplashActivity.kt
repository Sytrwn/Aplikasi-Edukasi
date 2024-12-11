package com.example.edukasi

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Timer untuk splash screen
        Handler(Looper.getMainLooper()).postDelayed({
            // Pindah ke MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Menghentikan SplashActivity agar tidak kembali saat tombol back ditekan
        }, 3000) // Durasi splash screen: 3000ms = 3 detik
    }
}
