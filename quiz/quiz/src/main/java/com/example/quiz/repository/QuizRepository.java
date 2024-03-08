package com.example.quiz.repository;

import com.example.quiz.entity.Quiz;
import org.springframework.data.repository.CrudRepository;

/**
 * Quiz 테이블: RepositoryImpl
 */
public interface QuizRepository extends CrudRepository<Quiz, Integer> {
}
