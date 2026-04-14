package com.pdm0126.taller1_00073523

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.*
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                val vm: QuizViewModel = viewModel()

                when (vm.currentScreen) {
                    "welcome" -> WelcomeScreen(vm)
                    "quiz" -> QuizScreen(vm)
                    "result" -> ResultScreen(vm)
                }
            }
        }
    }
}

@Composable
fun WelcomeScreen(vm: QuizViewModel) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("AndroidPedia", style = MaterialTheme.typography.headlineLarge)
        Text("¿Cuánto sabes de Android?", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(32.dp))
        Text("Harold Arturo Escobar López")
        Text("Carnet: 00073523")
        Spacer(Modifier.height(48.dp))
        Button(onClick = { vm.startQuiz() }) { Text("Comenzar Quiz") }
    }
}

@Composable
fun QuizScreen(vm: QuizViewModel) {
    val question = quizQuestions[vm.currentIndex]
    val isAnswered = vm.selectedOption != null

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Pregunta ${vm.currentIndex + 1} de 3")
        Text("Puntaje: ${vm.score}/3")

        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)) {
            Text(question.question, modifier = Modifier.padding(16.dp))
        }

        question.options.forEach { option ->
            val isCorrect = option == question.correctAnswer
            val isSelected = option == vm.selectedOption

            val containerColor = when {
                !isAnswered -> MaterialTheme.colorScheme.primary
                isCorrect -> Color(0xFF4CAF50)
                isSelected -> Color(0xFFF44336)
                else -> Color.Gray
            }

            Button(
                onClick = { vm.answerQuestion(isCorrect, option) },
                enabled = !isAnswered,
                colors = ButtonDefaults.buttonColors(containerColor = containerColor),
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            ) { Text(option) }
        }

        if (isAnswered) {
            Text(question.funFact, modifier = Modifier.padding(vertical = 12.dp))
            Button(onClick = { vm.nextQuestion() }) {
                Text(if (vm.currentIndex < 2) "Siguiente" else "Ver Resultado")
            }
        }
    }
}

@Composable
fun ResultScreen(vm: QuizViewModel) {
    val message = when (vm.score) {
        3 -> "¡Excelente! Eres un experto en Android."
        2 -> "¡Muy bien! Tienes bases sólidas."
        else -> "¡Sigue aprendiendo sobre el sistema!"
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Obtuviste ${vm.score} de 3", style = MaterialTheme.typography.headlineMedium)
        Text(message, modifier = Modifier.padding(16.dp))
        Button(onClick = { vm.startQuiz() }) { Text("Reiniciar Quiz") }
    }
}