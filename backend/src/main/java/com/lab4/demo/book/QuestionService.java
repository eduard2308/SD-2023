package com.lab4.demo.book;

import com.lab4.demo.book.model.Question;
import com.lab4.demo.book.model.dto.QuestionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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

    public QuestionDTO create(QuestionDTO bookDTO) {
        return questionMapper.toDto(questionRepository.save(
                questionMapper.fromDto(bookDTO)
        ));
    }

    public QuestionDTO edit(Long id, QuestionDTO questionDTO) {
        Question question = findById(id);
        question.setTitle(questionDTO.getTitle());
        question.setAuthor(questionDTO.getAuthor());
        question.setTag(questionDTO.getTag());
        question.setText(questionDTO.getText());
        question.setDate(questionDTO.getDate());
        return questionMapper.toDto(
                questionRepository.save(question)
        );
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
