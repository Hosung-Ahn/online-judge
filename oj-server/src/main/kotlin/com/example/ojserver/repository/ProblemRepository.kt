package com.example.ojserver.repository

import com.example.ojserver.entity.Problem
import org.springframework.data.jpa.repository.JpaRepository

interface ProblemRepository : JpaRepository<Problem, Long>
