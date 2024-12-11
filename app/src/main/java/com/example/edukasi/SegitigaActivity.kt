package com.example.edukasi

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random

class SegitigaActivity : AppCompatActivity() {

    private var currentQuestionIndex = 0
    private var score = 0
    private val totalQuestions = 5 // Total pertanyaan yang ingin Anda buat
    private val questions = mutableListOf<Pair<String, Double>>() // List untuk menyimpan pertanyaan

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_segitiga)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val imageView = findViewById<ImageView>(R.id.arrow_left)
        imageView.setOnClickListener {
            finish() // Menutup activity saat ini dan kembali ke halaman sebelumnya
        }


        val editTextAlas = findViewById<EditText>(R.id.editTextAlas)
        val editTextTinggi = findViewById<EditText>(R.id.editTextTinggi)
        val buttonHitung = findViewById<Button>(R.id.buttonHitung)
        val textViewHasil = findViewById<TextView>(R.id.textViewHasil)
        val buttonSoal = findViewById<Button>(R.id.buttonSoal)

        // Menghitung luas segitiga
        buttonHitung.setOnClickListener {
            val alasInput = editTextAlas.text.toString()
            val tinggiInput = editTextTinggi.text.toString()
            if (alasInput.isNotEmpty() && tinggiInput.isNotEmpty()) {
                val alas = alasInput.toDouble()
                val tinggi = tinggiInput.toDouble()
                val luas = 0.5 * alas * tinggi
                textViewHasil.text = "Hasil: Luas segitiga adalah $luas"
            } else {
                Toast.makeText(this, "Masukkan panjang alas dan tinggi!", Toast.LENGTH_SHORT).show()
            }
        }

        // Membuka soal latihan
        buttonSoal.setOnClickListener {
            generateQuestions()
            currentQuestionIndex = 0 // Reset index untuk pertanyaan
            showQuestion()
        }
    }

    private fun generateQuestions() {
        questions.clear() // Bersihkan daftar pertanyaan
        for (i in 1..totalQuestions) {
            val alas = Random.nextInt(1, 11) // Angka acak antara 1 hingga 10 untuk alas
            val tinggi = Random.nextInt(1, 11) // Angka acak antara 1 hingga 10 untuk tinggi
            val luasBenar = 0.5 * alas * tinggi // Hitung luas segitiga
            questions.add(Pair("Jika alas segitiga adalah $alas dan tinggi segitiga adalah $tinggi, berapakah luasnya?", luasBenar))
        }
    }

    private fun showQuestion() {
        if (currentQuestionIndex < questions.size) {
            val (pertanyaan, jawabanBenar) = questions[currentQuestionIndex]

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Soal ${currentQuestionIndex + 1}")
            builder.setMessage(pertanyaan)

            val input = EditText(this)
            input.hint = "Masukkan jawaban"
            builder.setView(input)

            builder.setPositiveButton("Cek Jawaban") { _, _ ->
                val jawabanUser = input.text.toString()
                if (jawabanUser.isNotEmpty() && jawabanUser.toDoubleOrNull() == jawabanBenar) {
                    Toast.makeText(this, "Jawaban benar!", Toast.LENGTH_SHORT).show()
                    score += 10
                } else {
                    Toast.makeText(this, "Jawaban salah! Jawaban yang benar adalah ${jawabanBenar.toInt()}", Toast.LENGTH_SHORT).show()
                }
                currentQuestionIndex++
                showQuestion()
            }

            builder.setNegativeButton("Batal") { _, _ ->
                Toast.makeText(this, "Latihan dihentikan.", Toast.LENGTH_SHORT).show()
            }

            builder.show()
        } else {
            showFinalScore()
        }
    }

    private fun showFinalScore() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Latihan Selesai")
        builder.setMessage("Skor akhir Anda adalah: $score")
        builder.setPositiveButton("Tutup") { _, _ ->
            // Reset untuk latihan baru jika diperlukan
            currentQuestionIndex = 0
            score = 0
        }
        builder.show()
    }
}
