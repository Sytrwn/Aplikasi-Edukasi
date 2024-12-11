package com.example.edukasi

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class PersegiActivity : AppCompatActivity() {

    private var currentQuestionIndex = 0
    private var score = 0
    private val totalQuestions = 5 // Total pertanyaan yang ingin Anda buat
    private val questions = mutableListOf<Pair<String, Int>>() // List untuk menyimpan pertanyaan

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_persegi)

        val imageView = findViewById<ImageView>(R.id.arrow_left)
        imageView.setOnClickListener {
            // Pindah ke MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val editTextSisi = findViewById<EditText>(R.id.editTextSisi)
        val buttonHitung = findViewById<Button>(R.id.buttonHitung)
        val textViewHasil = findViewById<TextView>(R.id.textViewHasil)
        val buttonSoal = findViewById<Button>(R.id.buttonSoal)

        // Menghitung luas persegi
        buttonHitung.setOnClickListener {
            val sisiInput = editTextSisi.text.toString()
            if (sisiInput.isNotEmpty()) {
                val sisi = sisiInput.toDouble()
                val luas = sisi * sisi
                textViewHasil.text = "Hasil: Luas persegi adalah $luas"
            } else {
                Toast.makeText(this, "Masukkan panjang sisi!", Toast.LENGTH_SHORT).show()
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
            val sisi = Random.nextInt(1, 21) // Angka acak antara 1 hingga 10
            questions.add(Pair("Jika sisi sebuah persegi adalah $sisi, berapakah luasnya?", sisi * sisi))
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
                    score += 20
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
