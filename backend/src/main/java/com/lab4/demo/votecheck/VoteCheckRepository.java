package com.lab4.demo.votecheck;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
public interface VoteCheckRepository extends JpaRepository<VoteCheck, Long> {
    VoteCheck findByUserIdAndQuestionId(Long userId, Long questionId);
    VoteCheck findByUserIdAndAnswerId(Long userId, Long answerId);
}
