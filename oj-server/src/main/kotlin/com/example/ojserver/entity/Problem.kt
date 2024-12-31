package com.example.ojserver.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Problem(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val title: String,
    val content: String,
    val timeLimit: Int,
    val memoryLimit: Int,
    var testCaseCount: Int = 0,
)
