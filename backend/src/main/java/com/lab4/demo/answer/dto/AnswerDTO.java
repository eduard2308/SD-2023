package com.lab4.demo.answer.dto;

import com.lab4.demo.book.model.Question;
import com.lab4.demo.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDTO {
    private Long id;
    private String text;
    private Long question_id;
    private Long user_id;
    private Date date;
    private byte[] image;
}
