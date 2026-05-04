package com.example.lifehackapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class QuizActivity : AppCompatActivity() {
    private var index = 0
    private var score = 0

    private lateinit var questionText: TextView
    private lateinit var feedbackText: TextView
    private lateinit var hackButton: Button
    private lateinit var mythButton: Button
    private lateinit var nextButton: Button

    private val questions = arrayOf(
        "Putting your phone in rice fixes water damage",
        "Using keyboard shortcuts improves productivity",
        "Drinking coffee completely dehydrates you",
        "Writing tasks down improves memory",
        "Charging phone overnight destroys battery"
    )

    private val answers = arrayOf(false, true, false, true, false)

    private val explanations = arrayOf(
        "Myth: Rice does not fix water damage.",
        "Hack: Saves time and improves productivity.",
        "Myth: Coffee still hydrates you.",
        "Hack: Writing improves memory.",
        "Myth: Phones prevent overcharging."
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        questionText = findViewById(R.id.questionText)
        feedbackText = findViewById(R.id.feedbackText)
        hackButton = findViewById(R.id.hackButton)
        mythButton = findViewById(R.id.mythButton)
        nextButton = findViewById(R.id.nextButton)

        loadQuestion()

        hackButton.setOnClickListener { checkAnswer(true) }
        mythButton.setOnClickListener { checkAnswer(false) }

        nextButton.setOnClickListener {
            index++
            if (index < questions.size) {
                loadQuestion()
            } else {
                // Navigate to ScoreActivity when finished
                val intent = Intent(this, ScoreActivity::class.java)
                intent.putExtra("score", score)
                intent.putExtra("total", questions.size)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun loadQuestion() {
        questionText.text = questions[index]
        feedbackText.text = ""
        nextButton.isEnabled = false
        hackButton.isEnabled = true
        mythButton.isEnabled = true
    }

    private fun checkAnswer(userAnswer: Boolean) {
        if (userAnswer == answers[index]) {
            score++
            feedbackText.text = "Correct!\n${explanations[index]}"
        } else {
            feedbackText.text = "Wrong!\n${explanations[index]}"
        }
        hackButton.isEnabled = false
        mythButton.isEnabled = false
        nextButton.isEnabled = true
    }
}
