package com.example.ojserver.controller

import com.example.ojserver.service.FileService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/file")
class FileController(
    val fileService: FileService,
) {
    @GetMapping("/upload")
    fun uploadFile(
        @RequestParam("file") file: MultipartFile,
    ): ResponseEntity<String> = ResponseEntity.ok(fileService.saveFile(file))
}
