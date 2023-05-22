package com.lab4.demo.answer;


import com.lab4.demo.answer.dto.AnswerDTO;
import com.lab4.demo.book.model.dto.QuestionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.lab4.demo.UrlMapping.*;

@RestController
@RequestMapping(ANSWERS)
@RequiredArgsConstructor
public class AnswerController {
    private final AnswerService answerService;

    @GetMapping
    public List<AnswerDTO> allAnswers() {
        return answerService.findAll();
    }

    @GetMapping(ADMIN)
    public List<AnswerDTO> allAnswersAdmin() {
        return answerService.findAll();
    }

    @GetMapping(ADMIN + ANSWERSBYQUESTION + ENTITY)
    public List<AnswerDTO> allAnswersForQuestion(@PathVariable Long id) {
        return answerService.findAllByQuestionId(id);
    }

    @PostMapping
    public AnswerDTO create(AnswerDTO answerDTO) {
        return answerService.create(answerDTO);
    }

    @PatchMapping(ADMIN)
    public AnswerDTO edit(@RequestParam("id") String id, @RequestParam(name = "image") MultipartFile image, @RequestParam(name = "answer") String answer) {
        return answerService.edit(id, image, answer);
    }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id) {
        answerService.delete(id);
    }

    @GetMapping(ENTITY)
    public AnswerDTO getAnswer(@PathVariable Long id) {
        return answerService.get(id);
    }
}
