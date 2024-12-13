package com.example.edukasi

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.PI
import kotlin.random.Random

class TabungActivity : AppCompatActivity() {

    private lateinit var editTextJariJari: EditText
    private lateinit var editTextTinggi: EditText
    private lateinit var buttonHitung: Button
    private lateinit var textViewHasil: TextView
    private lateinit var buttonSoal: Button

    private var currentQuestionIndex = 0
    private var score = 0
    private val totalQuestions = 5
    private val questions = mutableListOf<Pair<String, Int>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabung)

        val imageView = findViewById<ImageView>(R.id.arrow_left)
        imageView.setOnClickListener {
            finish()
        }

        editTextJariJari = findViewById(R.id.editTextJariJari)
        editTextTinggi = findViewById(R.id.editTextTinggi)
        buttonHitung = findViewById(R.id.buttonHitung)
        textViewHasil = findViewById(R.id.textViewHasil)
        buttonSoal = findViewById(R.id.buttonSoal)

        buttonHitung.setOnClickListener {
            hitungVolume()
        }

        buttonSoal.setOnClickListener {
            generateQuestions()
            currentQuestionIndex = 0
            showQuestion()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun hitungVolume() {
        val jariJari = editTextJariJari.text.toString().toDoubleOrNull()
        val tinggi = editTextTinggi.text.toString().toDoubleOrNull()

        if (jariJari != null && tinggi != null) {
            val volume = PI * jariJari * jariJari * tinggi
            textViewHasil.text = "Hasil: %.2f".format(volume)
        } else {
            textViewHasil.text = "Masukkan jari-jari dan tinggi yang valid"
        }
    }

    private fun generateQuestions() {
        questions.clear()
        for (i in 1..totalQuestions) {
            val jariJari = Random.nextInt(1, 11)
            val tinggi = Random.nextInt(1, 11)
            val volumeBenar = (PI * jariJari * jariJari * tinggi).toInt()
            questions.add(Pair("Jika jari-jari = $jariJari dan tinggi = $tinggi, berapakah volumenya?", volumeBenar))
        }
    }

    private fun showQuestion() {
        if (currentQuestionIndex < questions.size) {
            val (pertanyaan, jawabanBenar) = questions[currentQuestionIndex]

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Soal ${currentQuestionIndex + 1}")
            builder.setMessage(pertanyaan as CharSequence)

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
            currentQuestionIndex = 0
            score = 0
        }
        builder.show()
    }
}
