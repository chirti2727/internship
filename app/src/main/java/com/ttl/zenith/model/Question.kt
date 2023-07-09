package com.ttl.zenith.model

data class Question(
    val question: String,
    val options: List<String>,
    val answer: String
)

data class Quiz(
    val questions: List<Question>
)
