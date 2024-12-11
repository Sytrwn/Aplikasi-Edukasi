package com.example.edukasi

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.PI
import kotlin.random.Random

class LingkaranActivity : AppCompatActivity() {

    private lateinit var editTextJariJari: EditText
    private lateinit var buttonHitung: Button
    private lateinit var textViewHasil: TextView
    private lateinit var buttonSoal: Button

    private var currentQuestionIndex = 0
    private var score = 0
    private val totalQuestions = 5 // Total soal yang ingin ditampilkan
    private val questions = mutableListOf<Pair<String, Double>>() // List untuk menyimpan soal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lingkaran)

        val imageView = findViewById<ImageView>(R.id.arrow_left)
        imageView.setOnClickListener {
            finish() // Menutup activity saat ini dan kembali ke halaman sebelumnya
        }

        // Menghubungkan View dengan variabel
        editTextJariJari = findViewById(R.id.editTextJariJari)
        buttonHitung = findViewById(R.id.buttonHitung)
        textViewHasil = findViewById(R.id.textViewHasil)
        buttonSoal = findViewById(R.id.buttonSoal)

        // Mengatur listener untuk tombol hitung
        buttonHitung.setOnClickListener {
            hitungLuas()
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

    private fun hitungLuas() {
        val jariJari = editTextJariJari.text.toString().toDoubleOrNull()

        if (jariJari != null) {
            val luas = PI * jariJari * jariJari // Luas = π * r²
            textViewHasil.text = "Hasil: $luas"
        } else {
            textViewHasil.text = "Masukkan jari-jari yang valid"
        }
    }

    private fun generateQuestions() {
        questions.clear() // Bersihkan daftar soal sebelumnya
        for (i in 1..totalQuestions) {
            val jariJari = Random.nextInt(1, 11) // Angka acak antara 1 hingga 10 untuk jari-jari
            val luasBenar = PI * jariJari * jariJari // Hitung luas lingkaran
            questions.add(Pair("Jika jari-jari lingkaran adalah $jariJari, berapakah luasnya?", luasBenar))
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
                if (jawabanUser.isNotEmpty() && jawabanUser.toDoubleOrNull()?.let { Math.abs(it - jawabanBenar) < 0.01 } == true) {
                    Toast.makeText(this, "Jawaban benar!", Toast.LENGTH_SHORT).show()
                    score += 10
                } else {
                    Toast.makeText(this, "Jawaban salah! Jawaban yang benar adalah %.2f".format(jawabanBenar), Toast.LENGTH_SHORT).show()
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
