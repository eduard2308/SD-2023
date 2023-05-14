package com.lab4.demo.answer;

import com.lab4.demo.answer.Answer;
import com.lab4.demo.answer.dto.AnswerDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface AnswerMapper {

        AnswerDTO toDto(Answer answer);

        Answer fromDto(AnswerDTO answerDTO);
}
