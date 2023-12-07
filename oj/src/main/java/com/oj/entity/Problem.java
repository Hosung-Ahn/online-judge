package com.oj.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Problem {
    @Id @GeneratedValue
    private Long id;
    private String title;
    private String description;
    private Integer timeLimitSec;
    private Integer memoryLimitMb;
    private Integer testCount;

    @Builder
    public Problem(String title, String description, Integer timeLimitSec, Integer memoryLimitMb, Integer testCount) {
        this.title = title;
        this.description = description;
        this.timeLimitSec = timeLimitSec;
        this.memoryLimitMb = memoryLimitMb;
        this.testCount = testCount;
    }
}
