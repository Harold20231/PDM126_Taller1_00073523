package com.pdm0126.taller1_00073523

data class Question(
    val id: Int,
    val question: String,
    val options: List<String>,
    val correctAnswer: String,
    val funFact: String
)
