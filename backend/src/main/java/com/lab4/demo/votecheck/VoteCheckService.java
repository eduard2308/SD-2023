package com.lab4.demo.votecheck;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoteCheckService {
    private final VoteCheckRepository voteCheckRepository;
    

}
