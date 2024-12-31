package com.example.ojserver.repository

import com.example.ojserver.entity.TestCase
import org.springframework.data.jpa.repository.JpaRepository

interface TestCaseRepository : JpaRepository<TestCase, Long>
