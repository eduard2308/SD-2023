package com.lab4.demo.book;

import com.lab4.demo.book.model.dto.QuestionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.lab4.demo.UrlMapping.*;

@RestController
@RequestMapping(ITEMS)
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping
    public List<QuestionDTO> allQuestions() {
        return questionService.findAll();
    }

    @GetMapping(ADMIN)
    public ResponseEntity<List<QuestionDTO>> allQuestionsAdmin() {
        List<QuestionDTO> dtos = questionService.findAll();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping
        public QuestionDTO create(@RequestParam(name = "image") MultipartFile image, @RequestParam(name = "question") String question, @RequestParam(name = "author") String author) {
        return questionService.create(image, question, author);
    }

    /*@PatchMapping(ENTITY)
    public ItemDTO changeName(@PathVariable Long id, @RequestBody String newName) {
        return itemService.changeName(id, newName);
    }*/

    @PatchMapping(ADMIN)
    public QuestionDTO edit(@RequestParam("id") String id, @RequestParam(name = "image") MultipartFile image, @RequestParam(name = "question") String question) {
        return questionService.edit(id, image, question);
    }

    @PatchMapping
    public QuestionDTO editBasic(@RequestParam("id") String id, @RequestParam(name = "image") MultipartFile image, @RequestParam(name = "question") String question) {
        return questionService.edit(id, image, question);
    }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id) {
        questionService.delete(id);
    }

    @DeleteMapping(ADMIN + ENTITY)
    public ResponseEntity<?> deleteAdmin(@PathVariable("id") Long id){
        questionService.delete(id);
        return ResponseEntity.ok().body(new QuestionDTO());
    }

    @GetMapping(ENTITY)
    public QuestionDTO getQuestion(@PathVariable Long id) {
        return questionService.get(id);
    }

}
