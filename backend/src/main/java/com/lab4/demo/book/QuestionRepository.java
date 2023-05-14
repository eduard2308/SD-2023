package com.lab4.demo.book;

import com.lab4.demo.book.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long>, JpaSpecificationExecutor<Question> {
    List<Question> findAllByTitleLikeOrTextLike(String title, String text);

    List<Question> findAllByTitleLike(String title);

    // or, more dynamically...
    List<Question> findAllByTitleLike(String title, Sort sorting);

    Page<Question> findAllByTitleLike(String title, Pageable pageable);


    // what if we had 5+ fields to search on...?
    // problem with the fixed set of criterias
    // wouldn't it be cool to have a set of atomic predicates to combine at will?
}
