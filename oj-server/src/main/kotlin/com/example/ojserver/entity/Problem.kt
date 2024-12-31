package com.example.ojserver.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany

@Entity
class Problem(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val title: String,
    val content: String,
    val timeLimit: Int,
    val memoryLimit: Int,
    @OneToMany(mappedBy = "problem", cascade = [CascadeType.ALL], orphanRemoval = true)
    val testCases: List<TestCase> = mutableListOf(),
)

@Entity
class TestCase(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val input: String,
    val output: String,
    @ManyToOne
    @JoinColumn(name = "problem_id", nullable = false)
    val problem: Problem,
)
