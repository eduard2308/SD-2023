package com.lab4.demo.answer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lab4.demo.answer.dto.AnswerDTO;
import com.lab4.demo.question.model.Question;
import com.lab4.demo.question.model.dto.QuestionDTO;
import com.lab4.demo.user.UserRepository;
import com.lab4.demo.user.model.User;
import com.lab4.demo.votecheck.VoteCheck;
import com.lab4.demo.votecheck.VoteCheckRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final AnswerMapper answerMapper;

    @Autowired
    private VoteCheckRepository voteCheckRepository;
    @Autowired
    private UserRepository userRepository;

    public AnswerDTO create(MultipartFile image, String answer, String question_id, String user_id) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            AnswerDTO answerDTO = objectMapper.readValue(answer, AnswerDTO.class);
            Answer answerObj = answerMapper.fromDto(answerDTO);
            answerObj.setImage(image.getBytes());
            answerObj.setQuestion_id(Long.parseLong(question_id));
            answerObj.setUser_id(Long.parseLong(user_id));
            answerObj.setDate(new java.util.Date());
            return answerMapper.toDto(answerRepository.save(answerObj));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public AnswerDTO edit(String id, MultipartFile image, String answer) {
        Answer answerObj = answerMapper.fromDto(findById(Long.parseLong(id)));
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            AnswerDTO answerDTO = objectMapper.readValue(answer, AnswerDTO.class);
            answerObj.setText(answerDTO.getText());
            answerObj.setImage(image.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return answerMapper.toDto(answerRepository.save(answerObj));
    }

    public void delete(Long id) {
        answerRepository.deleteById(id);
    }

    public AnswerDTO findById(Long id) {
        return answerMapper.toDto(answerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Answer not found: " + id)));
    }

    public List<AnswerDTO> findAll() {
        return answerRepository.findAll().stream()
                .map(answerMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<AnswerDTO> findAllByQuestionId(Long id) {
        return answerRepository.findAllByQuestionId(id).stream()
                .map(answerMapper::toDto)
                .collect(Collectors.toList());
    }

    public boolean findByUserIdAndAnswerId(Long userId, Long answerId) {
        VoteCheck voteCheck = voteCheckRepository.findByUserIdAndAnswerId(userId, answerId);
        if (voteCheck == null) {
            return false;
        }
        return true;
    }

    public AnswerDTO upVoteAnswer(Long answerId, Long userId) {
        Answer answerObj = answerMapper.fromDto(findById(answerId));
        if(!findByUserIdAndAnswerId(userId, answerId)) {
            VoteCheck voteCheck = new VoteCheck();
            voteCheck.setUserId(userId);
            voteCheck.setAnswerId(answerId);
            updateScoreUser(userId,"up",answerObj);
            voteCheckRepository.save(voteCheck);
            answerObj.setVotes(answerObj.getVotes() + 1);
        }
        return answerMapper.toDto(answerRepository.save(answerObj));
    }

    public AnswerDTO downVoteAnswer(Long answerId, Long userId) {
        Answer answerObj = answerMapper.fromDto(findById(answerId));
        if(!findByUserIdAndAnswerId(userId, answerId)) {
            VoteCheck voteCheck = new VoteCheck();
            voteCheck.setUserId(userId);
            voteCheck.setAnswerId(answerId);
            voteCheckRepository.save(voteCheck);
            updateScoreUser(userId,"down",answerObj);
            answerObj.setVotes(answerObj.getVotes() - 1);
        }
        return answerMapper.toDto(answerRepository.save(answerObj));
    }
    private void updateScoreUser(Long userId, String voteType,Answer answer){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
        User answerAuthor = userRepository.findById(answer.getUser_id())
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + answer.getUser_id()));
        if(voteType.equals("up")) {
            answerAuthor.setScore(answerAuthor.getScore() + 5);
        } else {
            answerAuthor.setScore(answerAuthor.getScore() - 2.5);
            user.setScore(user.getScore() - 1.5);
        }
        userRepository.save(answerAuthor);
        userRepository.save(user);
    }

    public AnswerDTO get(Long id) {
        return findById(id);
    }

    public Double getScoreUserByAnswerId(Long id){
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Answer not found: " + id));
        User user = userRepository.findById(answer.getUser_id())
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + answer.getUser_id()));
        return user.getScore();
    }
}
