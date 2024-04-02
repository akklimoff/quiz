package kg.attractor.quiz_project.dao;

import kg.attractor.quiz_project.model.Quiz;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.PreparedStatement;


@Component
@RequiredArgsConstructor
public class QuizDao {
    private final JdbcTemplate jdbcTemplate;

    public void createQuiz(Quiz quiz) {

        String sql = "INSERT INTO QUIZZES (title, description, creator_username) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, quiz.getTitle(), quiz.getDescription(), quiz.getCreatorUsername());
    }

    public Quiz save(Quiz quiz) {
        final String sql = "INSERT INTO quizzes (title, description, creator_username) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, quiz.getTitle());
            ps.setString(2, quiz.getDescription());
            ps.setString(3, quiz.getCreatorUsername());
            return ps;
        }, keyHolder);

        quiz.setId(keyHolder.getKey().intValue());
        return quiz;
    }

}
