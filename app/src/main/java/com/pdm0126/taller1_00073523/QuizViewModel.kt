package com.pdm0126.taller1_00073523

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class QuizViewModel : ViewModel() {
    var currentScreen by mutableStateOf("welcome")
    var currentIndex by mutableIntStateOf(0)
    var score by mutableIntStateOf(0)
    var selectedOption by mutableStateOf<String?>(null)

    fun startQuiz() {
        score = 0
        currentIndex = 0
        selectedOption = null
        currentScreen = "quiz"
    }

    fun answerQuestion(isCorrect: Boolean, option: String) {
        selectedOption = option
        if (isCorrect) score++
    }

    fun nextQuestion() {
        if (currentIndex < quizQuestions.size - 1) {
            currentIndex++
            selectedOption = null
        } else {
            currentScreen = "result"
        }
    }
}