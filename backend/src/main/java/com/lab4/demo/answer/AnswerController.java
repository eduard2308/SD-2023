package com.lab4.demo.answer;


import com.lab4.demo.answer.dto.AnswerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public AnswerDTO create(AnswerDTO answerDTO) {
        return answerService.create(answerDTO);
    }

    @PatchMapping(ENTITY)
    public AnswerDTO edit(@RequestBody Long id, @RequestBody AnswerDTO answerDTO) {
        return answerService.edit(id, answerDTO);
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
