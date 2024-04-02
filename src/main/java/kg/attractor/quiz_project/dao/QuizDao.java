package kg.attractor.quiz_project.dao;

import kg.attractor.quiz_project.model.Quiz;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.util.List;


@Component
@RequiredArgsConstructor
public class QuizDao {
    private final JdbcTemplate jdbcTemplate;

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

    public List<Quiz> findAll() {
        final String sql = "SELECT * FROM quizzes";
        return jdbcTemplate.query(sql, (rs, rowNum) -> Quiz.builder()
                .id(rs.getInt("id"))
                .title(rs.getString("title"))
                .description(rs.getString("description"))
                .creatorUsername(rs.getString("creator_username"))
                .build());
    }

}
