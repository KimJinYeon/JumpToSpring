package com.example.demo.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class AnswerRepositoryTest {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @BeforeEach
    void beforeEach(){
        questionRepository.deleteAll();
        answerRepository.deleteAll();
    }

    @Test
    @DisplayName("답변 데이터 생성 후 저장하기")
    void createAnswerAndSave() {
        // given

        Question q1 = new Question();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        q1.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q1);

        // when

        Answer answer = new Answer();
        answer.setContent("네, 자동으로 생성됩니다.");
        answer.setQuestion(q1);
        answer.setCreateDate(LocalDateTime.now());
        this.answerRepository.save(answer);

        // then

        assertEquals(answer.getQuestion().getId(), q1.getId());
    }
}
