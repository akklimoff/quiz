package kg.attractor.quiz_project.dao;

import kg.attractor.quiz_project.dto.QuizResultDto;
import kg.attractor.quiz_project.model.Quiz;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import java.sql.PreparedStatement;
import java.sql.Statement;
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

    public Quiz findById(int id) {
        final String sql = "SELECT * FROM quizzes WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Quiz.class));
    }

    public boolean existsById(int quizId) {
        final String sql = "SELECT COUNT(*) FROM quizzes WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{quizId}, Integer.class);
        return count != null && count > 0;
    }

    public QuizResultDto saveQuizResult(QuizResultDto result) {
        final String sql = "INSERT INTO quiz_results (quiz_id, user_username, score) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, result.getQuizId());
            ps.setString(2, result.getUserUsername());
            ps.setInt(3, result.getScore());
            return ps;
        }, keyHolder);
        result.setId(keyHolder.getKey().intValue());

        return result;
    }
}
