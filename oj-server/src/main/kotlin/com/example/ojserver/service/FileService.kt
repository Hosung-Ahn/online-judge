package com.example.ojserver.service

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.IOException
import java.nio.file.Paths

@Service
class FileService {
    fun saveFile(
        file: MultipartFile,
        fileName: String,
    ): String {
        if (file.isEmpty) {
            throw IllegalArgumentException("File is empty")
        }

        // 현재 프로젝트 루트 기준으로 'uploads/' 경로 설정
        val path = Paths.get("").toAbsolutePath().toString() + "/uploads/"
        val directory = File(path)
        if (!directory.exists()) {
            directory.mkdirs()
        }

        try {
            val filePath = "$path/$fileName"
            file.transferTo(File(filePath))
            return fileName
        } catch (e: IOException) {
            e.printStackTrace()
            throw IllegalArgumentException("Failed to save file")
        }
    }
}
