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

class KubusActivity : AppCompatActivity() {

    private lateinit var editTextSisi: EditText
    private lateinit var buttonHitung: Button
    private lateinit var textViewHasil: TextView
    private lateinit var buttonSoal: Button

    private var currentQuestionIndex = 0
    private var score = 0
    private val totalQuestions = 5 // Total soal yang ingin ditampilkan
    private val questions = mutableListOf<Pair<String, Int>>() // List untuk menyimpan soal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_kubus)

        val imageView = findViewById<ImageView>(R.id.arrow_left)
        imageView.setOnClickListener {
            finish() // Menutup activity saat ini dan kembali ke halaman sebelumnya
        }

        // Menghubungkan View dengan variabel
        editTextSisi = findViewById(R.id.editTextSisi)
        buttonHitung = findViewById(R.id.buttonHitung)
        textViewHasil = findViewById(R.id.textViewHasil)
        buttonSoal = findViewById(R.id.buttonSoal)

        // Mengatur listener untuk tombol hitung
        buttonHitung.setOnClickListener {
            hitungLuasPermukaan()
        }

        // Mengatur listener untuk tombol soal
        buttonSoal.setOnClickListener {
            generateQuestions()
            currentQuestionIndex = 0 // Reset index untuk soal baru
            showQuestion()
        }

        // Menangani window insets untuk edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun hitungLuasPermukaan() {
        val sisi = editTextSisi.text.toString().toIntOrNull()

        if (sisi != null) {
            val luasPermukaan = 6 * sisi * sisi // Luas permukaan kubus = 6 * s²
            textViewHasil.text = "Hasil: $luasPermukaan"
        } else {
            textViewHasil.text = "Masukkan panjang sisi yang valid"
        }
    }

    private fun generateQuestions() {
        questions.clear() // Bersihkan daftar soal sebelumnya
        for (i in 1..totalQuestions) {
            val sisi = Random.nextInt(1, 11) // Angka acak antara 1 hingga 10 untuk panjang sisi
            val luasBenar = 6 * sisi * sisi // Hitung luas permukaan kubus
            questions.add(Pair("Jika panjang sisi kubus adalah $sisi, berapakah luas permukaannya?", luasBenar))
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
                if (jawabanUser.isNotEmpty() && jawabanUser.toIntOrNull() == jawabanBenar) {
                    Toast.makeText(this, "Jawaban benar!", Toast.LENGTH_SHORT).show()
                    score += 10
                } else {
                    Toast.makeText(this, "Jawaban salah! Jawaban yang benar adalah $jawabanBenar", Toast.LENGTH_SHORT).show()
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
