package com.oj.controller;

import com.oj.dto.request.ProblemCreateRequestDto;
import com.oj.service.problem.ProblemCreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/problem")
public class ProblemController {
    private final ProblemCreateService problemCreateService;

    @PostMapping("/create")
    public ResponseEntity<String> createProblem(@RequestBody ProblemCreateRequestDto requestDto) {
        problemCreateService.createProblem(requestDto);
        return ResponseEntity.ok("success");
    }
}
