package com.example.quiz.repository;

import com.example.quiz.entity.Quiz;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Quiz 테이블: RepositoryImpl
 */
public interface QuizRepository extends CrudRepository<Quiz, Integer> {
    // quiz 테이블의 id 칼럼을 랜덤으로 1건 가져오는 SQL
    @Query("SELECT id FROM quiz ORDER BY RANDOM() limit 1")
    Integer getRandomId();
}
