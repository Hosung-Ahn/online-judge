package com.example.ojserver.controller

import com.example.ojserver.service.FileService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/file")
class FileController(
    val fileService: FileService,
)
