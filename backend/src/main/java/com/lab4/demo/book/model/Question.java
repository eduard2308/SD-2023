package com.lab4.demo.book.model;

import com.lab4.demo.answer.Answer;
import com.lab4.demo.user.model.User;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 512, nullable = false)
    private String title;

    @Column(length = 512, nullable = false)
    private String tag;

    @Column(length = 8000)
    private String text;

    private Long user_id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id" , insertable = false, updatable = false)
    private User author;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Answer> answers;


    private Date date;

    private byte[] image;
}
