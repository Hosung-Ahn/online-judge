package com.oj.controller;

import com.oj.dto.JudgeResultDto;
import com.oj.dto.SingleJudgeResultDto;
import com.oj.dto.request.JudgeRequestDto;
import com.oj.service.judge.JudgeService;
import com.oj.service.judge.Language;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class JudgeController {
    private final JudgeService judgeService;

    @PostMapping("/judge")
    public ResponseEntity<JudgeResultDto> judge(@RequestBody JudgeRequestDto requestDto) {
        Long problemId = requestDto.getProblemId();
        String sourceCode = requestDto.getSourceCode();
        Language language = Language.valueOf(requestDto.getLanguage());
        List<SingleJudgeResultDto> judge = judgeService.judge(problemId, sourceCode, language);
        return ResponseEntity.ok(new JudgeResultDto(problemId, judge));
    }
}
