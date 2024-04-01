package kg.attractor.quiz_project.dao;

import kg.attractor.quiz_project.model.Quiz;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
@RequiredArgsConstructor
public class QuizDao {
    private final JdbcTemplate jdbcTemplate;

    public void createQuiz(Quiz quiz) {

        String sql = "INSERT INTO QUIZZES (title, description, creator_username) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, quiz.getTitle(), quiz.getDescription(), quiz.getCreator_username());
    }

}
