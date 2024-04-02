package kg.attractor.quiz_project.dao;

import kg.attractor.quiz_project.model.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Component
@RequiredArgsConstructor
public class QuestionDao {
    private final JdbcTemplate jdbcTemplate;

    public Question save(Question question) {
        final String sql = "INSERT INTO questions (quiz_id, question_text) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, question.getQuizId());
            ps.setString(2, question.getQuestionText());
            return ps;
        }, keyHolder);

        question.setId(keyHolder.getKey().intValue());
        return question;
    }

    public int countByQuizId(int quizId) {
        final String sql = "SELECT COUNT(*) FROM questions WHERE quiz_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{quizId}, Integer.class);
    }

    public List<Question> findByQuizId(int quizId) {
        final String sql = "SELECT * FROM questions WHERE quiz_id = ?";
        return jdbcTemplate.query(sql, new Object[]{quizId}, (rs, rowNum) -> Question.builder()
                .id(rs.getInt("id"))
                .quizId(rs.getInt("quiz_id"))
                .questionText(rs.getString("question_text"))
                .build());
    }
}
