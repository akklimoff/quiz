package kg.attractor.quiz_project.dao;

import kg.attractor.quiz_project.model.QuizResult;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class QuizResultDao {
    private final JdbcTemplate jdbcTemplate;

    public void save(QuizResult quizResult) {
        final String sql = "UPDATE quiz_results SET rating = ? WHERE id = ?";
        jdbcTemplate.update(sql, quizResult.getRating(), quizResult.getId());
    }

    public Optional<QuizResult> findByQuizIdAndUsername(int quizId, String userUsername) {
        final String sql = "SELECT * FROM quiz_results WHERE quiz_id = ? AND user_username = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new Object[]{quizId, userUsername}, (rs, rowNum) ->
                    new QuizResult(
                            rs.getInt("id"),
                            rs.getString("user_username"),
                            rs.getInt("quiz_id"),
                            rs.getInt("score"),
                            rs.getObject("rating") != null ? rs.getInt("rating") : null
                    )
            ));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<QuizResult> findByQuizId(int quizId) {
        final String sql = "SELECT * FROM quiz_results WHERE quiz_id = ?";
        return jdbcTemplate.query(sql, new Object[]{quizId}, (rs, rowNum) -> {
            QuizResult quizResult = new QuizResult();

            quizResult.setId(rs.getInt("id"));
            quizResult.setUserUsername(rs.getString("user_username"));
            quizResult.setQuizId(rs.getInt("quiz_id"));
            quizResult.setScore(rs.getInt("score"));
            quizResult.setRating(rs.getObject("rating") != null ? rs.getInt("rating") : null);


            return quizResult;
        });
    }
}
