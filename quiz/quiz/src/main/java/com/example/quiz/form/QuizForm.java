package com.example.quiz.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * Form 화면에 표시되는 내용과 대응되는 폼
 * Form
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizForm {
    /**
     * 식별 ID
     */
    private Integer id;

    /**
     * 퀴즈 내용
     */
    @NotBlank // 단일 항목 검사로 빈(null) 객체를 허용하지 않는 어노테이션
    private String question;

    /**
     * 퀴즈 해답
     */
    private Boolean answer;

    /**
     * 작성자
     */
    @NotBlank
    private String author;

    /**
     * 등록 또는 변경 판단용
     * true : 등록
     * false : 변경
     */
    private Boolean newQuiz;
}
