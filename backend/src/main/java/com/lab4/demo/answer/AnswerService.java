package com.lab4.demo.answer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lab4.demo.answer.dto.AnswerDTO;
import lombok.RequiredArgsConstructor;
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

    public AnswerDTO create(AnswerDTO answerDTO) {
        Answer answer = answerMapper.fromDto(answerDTO);
        return answerMapper.toDto(answerRepository.save(answer));
    }

    public AnswerDTO edit(String id, MultipartFile image, String answer) {
        Answer answerObj = findById(Long.parseLong(id));
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

    public Answer findById(Long id) {
        return answerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Answer not found: " + id));
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

    public List<AnswerDTO> findAllByTitleLikeOrTextLike(String text) {
        return answerRepository.findAllByTextLike(text).stream()
                .map(answerMapper::toDto)
                .collect(Collectors.toList());
    }


    public List<AnswerDTO> findAllByTitleLike(String text, Sort sorting) {
        return answerRepository.findAllByTextLike(text, sorting).stream()
                .map(answerMapper::toDto)
                .collect(Collectors.toList());
    }

    public Page<AnswerDTO> findAllByTitleLike(String text, Pageable pageable) {
        return answerRepository.findAllByTextLike(text, pageable).map(answerMapper::toDto);
    }

    public AnswerDTO get(Long id) {
        return answerMapper.toDto(findById(id));
    }
}
