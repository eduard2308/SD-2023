package com.lab4.demo.answer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository 
public interface AnswerRepository extends JpaRepository<Answer, Long>, JpaSpecificationExecutor<Answer> {
    List<Answer> findAllByTextLike(String text);

    List<Answer> findAllByAuthor(Long author_id);

    List<Answer> findAllByQuestionId(Long question_id);

    // or, more dynamically...
    List<Answer> findAllByTextLike(String text, Sort sorting);

    Page<Answer> findAllByTextLike(String text, Pageable pageable);
    
}
