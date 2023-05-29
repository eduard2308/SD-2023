package com.lab4.demo.question;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lab4.demo.answer.Answer;
import com.lab4.demo.question.model.Question;
import com.lab4.demo.question.model.dto.QuestionDTO;
import com.lab4.demo.user.UserRepository;
import com.lab4.demo.user.model.User;
import com.lab4.demo.votecheck.VoteCheck;
import com.lab4.demo.votecheck.VoteCheckRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;

    @Autowired
    private VoteCheckRepository voteCheckRepository;
    @Autowired
    private UserRepository userRepository;

    private Question findById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Question not found: " + id));
    }

    public List<QuestionDTO> findAll() {
        return questionRepository.findAll().stream()
                .map(questionMapper::toDto)
                .collect(Collectors.toList());
    }

    public QuestionDTO create(MultipartFile image, String question, String author) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            QuestionDTO questionDTO = objectMapper.readValue(question, QuestionDTO.class);
            Question questionObj = questionMapper.fromDto(questionDTO);
            questionObj.setImage(image.getBytes());
            User user = objectMapper.readValue(author, User.class);
            questionObj.setUser_id(user.getId());
            questionObj.setDate(new java.util.Date());
            questionObj.setVotes(0);
            return questionMapper.toDto(questionRepository.save(questionObj));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public QuestionDTO edit(String id, MultipartFile image, String question) {
        Question questionObj = findById(Long.parseLong(id));
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            QuestionDTO questionDTO = objectMapper.readValue(question, QuestionDTO.class);
            questionObj.setTitle(questionDTO.getTitle());
            questionObj.setText(questionDTO.getText());
            questionObj.setTag(questionDTO.getTag());
            questionObj.setImage(image.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return questionMapper.toDto(questionRepository.save(questionObj));
    }

    private boolean findByUserIdAndQuestionId(Long userId, Long questionId) {
        VoteCheck voteCheck = voteCheckRepository.findByUserIdAndQuestionId(userId, questionId);
        if (voteCheck == null) {
            return false;
        }
        return true;
    }

    public QuestionDTO upVoteQuestion(Long questionId, Long userId) {
        Question questionObj = findById(questionId);
        if(!findByUserIdAndQuestionId(userId, questionId)) {
            VoteCheck voteCheck = new VoteCheck();
            voteCheck.setUserId(userId);
            voteCheck.setQuestionId(questionId);
            voteCheckRepository.save(voteCheck);
            updateScoreUser("up", questionObj);
            questionObj.setVotes(questionObj.getVotes() + 1);
        }
        return questionMapper.toDto(questionRepository.save(questionObj));
    }

    public QuestionDTO downVoteQuestion(Long questionId, Long userId) {
        Question questionObj = findById(questionId);
        if(!findByUserIdAndQuestionId(userId, questionId)) {
            VoteCheck voteCheck = new VoteCheck();
            voteCheck.setUserId(userId);
            voteCheck.setQuestionId(questionId);
            voteCheckRepository.save(voteCheck);
            updateScoreUser("down", questionObj);
            questionObj.setVotes(questionObj.getVotes() - 1);
        }
        return questionMapper.toDto(questionRepository.save(questionObj));
    }

    private void updateScoreUser(String voteType, Question question){
        User questionAuthor = userRepository.findById(question.getUser_id())
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + question.getUser_id()));
        if(voteType.equals("up")) {
            questionAuthor.setScore(questionAuthor.getScore() + 2.5);
        } else {
            questionAuthor.setScore(questionAuthor.getScore() - 1.5);
        }
        userRepository.save(questionAuthor);
    }

    public QuestionDTO get(Long id) {
        return questionMapper.toDto(findById(id));
    }

    public void delete(Long id) {
        questionRepository.deleteById(id);
    }

    public void deleteAll() {
        questionRepository.deleteAll();
    }

    public Double getScoreUserByQuestionId(Long id){
        Question question = findById(id);
        User questionAuthor = userRepository.findById(question.getUser_id())
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + question.getUser_id()));
        return questionAuthor.getScore();
    }
}
