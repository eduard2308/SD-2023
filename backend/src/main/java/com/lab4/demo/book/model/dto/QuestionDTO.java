package com.lab4.demo.book.model.dto;

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
public class QuestionDTO {
    private Long id;
    private String title;
    private String tag;
    private String text;
    private User author;
    private Date date;
    private byte[] image;
}
