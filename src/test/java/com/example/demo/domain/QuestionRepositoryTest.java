package com.example.demo.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @BeforeEach
    void beforeEach(){
        questionRepository.deleteAll();
    }

    @Test
    @DisplayName("질문 2개 등록하기")
    void saveQuestionsTest() {
        Question q1 = new Question();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        q1.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q1);

        Question q2 = new Question();
        q2.setSubject("스프링 부트 모델 질문입니다.");
        q2.setContent("id는 자동으로 생성되나요?");
        q2.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q2);
    }

    @Test
    @DisplayName("질문 목록 가져오기")
    void getAllQuestionsTest() {
        Question q1 = new Question();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        q1.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q1);

        Question q2 = new Question();
        q2.setSubject("스프링 부트 모델 질문입니다.");
        q2.setContent("id는 자동으로 생성되나요?");
        q2.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q2);

        List<Question> all = this.questionRepository.findAll();
        assertEquals(2, all.size());
    }

    @Test
    @DisplayName("아이디로 질문 찾기")
    void findQuestionByIdTest() {
        Optional<Question> optionalQuestion1 = this.questionRepository.findById(1);
        assertFalse(optionalQuestion1.isPresent());

        Question q1 = new Question();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        q1.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q1);
        Integer questionId = q1.getId();

        List<Question> all = this.questionRepository.findAll();
        assertEquals(1, all.size());


        Optional<Question> optionalQuestion2 = this.questionRepository.findById(questionId);
        assertTrue(optionalQuestion2.isPresent());

        Question question = optionalQuestion2.get();
        assertEquals("sbb가 무엇인가요?", question.getSubject());
    }

    @Test
    @DisplayName("제목으로 조회")
    void findBySubjectTest() {
        Question q1 = new Question();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        q1.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q1);
        Integer questionId = q1.getId();

        Question optionalQuestion = this.questionRepository.findBySubject("sbb가 무엇인가요?");
        assertEquals(questionId, optionalQuestion.getId());
    }

    @Test
    @DisplayName("제목과 내용으로 조회")
    void findBySubjectAndContent() {
        //given
        Question q1 = new Question();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        q1.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q1);
        Integer questionId = q1.getId();

        //when
        Question question = this.questionRepository.findBySubjectAndContent(
                "sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다."
        );

        //then
        assertEquals(questionId, question.getId());
    }

    @Test
    @DisplayName("like로 조회")
    void findBySubjectLikeTest(){
        //given
        Question q1 = new Question();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        q1.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q1);
        Integer questionId = q1.getId();

        //when
        List<Question> questionList = this.questionRepository.findBySubjectLike("sbb%");
        Question question = questionList.get(0);

        //then
        assertEquals("sbb가 무엇인가요?", question.getSubject());
    }

    @Test
    @DisplayName("질문 제목 수정하기")
    void testSetQuestionSubject() {
        // given
        Question q1 = new Question();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        q1.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q1);
        Integer questionId = q1.getId();

        // when
        Optional<Question> questionOptional = this.questionRepository.findById(questionId);
        assertTrue(questionOptional.isPresent());
        Question questionSubjectChanged = questionOptional.get();
        questionSubjectChanged.setSubject("수정된 제목");
        this.questionRepository.save(questionSubjectChanged);
        List<Question> questionList = this.questionRepository.findAll();
        Question questionTest = questionList.get(0);

        // then
        assertEquals(questionTest.getSubject(), "수정된 제목");
        assertSame(questionTest, questionSubjectChanged);

    }

    @Test
    @DisplayName("질문 삭제하기")
    void deleteQuestionTest() {
        // given
        Question q1 = new Question();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        q1.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q1);

        Question q2 = new Question();
        q2.setSubject("스프링 부트 모델 질문입니다.");
        q2.setContent("id는 자동으로 생성되나요?");
        q2.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q2);

        assertEquals(2, this.questionRepository.count());

        // when
        List<Question> questionList = this.questionRepository.findAll();
        Question questionToBeDeleted = questionList.get(0);
        this.questionRepository.delete(questionToBeDeleted);

        assertEquals(1, this.questionRepository.count());

    }
}
