package com.oj;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
@RequiredArgsConstructor
public class Controller {
    private final FileService fileService;
    private final DockerService dockerService;
    @PostMapping("/submit")
    public String submit(@RequestBody SubmitDto submitDto) {
        File file = fileService.makeFile(submitDto.getLanguage(), submitDto.getSourceCode());
        dockerService.runCodeInDocker(file, submitDto.getLanguage());
        file.delete();
        System.out.println();
        return "submit";
    }
}
