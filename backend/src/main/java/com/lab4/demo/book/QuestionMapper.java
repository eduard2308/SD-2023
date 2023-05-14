package com.lab4.demo.book;

import com.lab4.demo.book.model.Question;
import com.lab4.demo.book.model.dto.QuestionDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    QuestionDTO toDto(Question question);

    Question fromDto(QuestionDTO questionDTO);

}
