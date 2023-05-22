package com.lab4.demo.book;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lab4.demo.book.model.Question;
import com.lab4.demo.book.model.dto.QuestionDTO;
import com.lab4.demo.user.model.User;
import lombok.RequiredArgsConstructor;
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

    public QuestionDTO changeTitle(Long id, String newTitle) {
        Question question = findById(id);
        question.setTitle(newTitle);
        return questionMapper.toDto(
                questionRepository.save(question)
        );
    }

    public QuestionDTO changeText(Long id, String newText) {
        Question question = findById(id);
        question.setText(newText);
        return questionMapper.toDto(
                questionRepository.save(question)
        );
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
}
