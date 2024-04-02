package kg.attractor.quiz_project.dao;

import kg.attractor.quiz_project.model.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.RowMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OptionDao {
    private final JdbcTemplate jdbcTemplate;

    public void saveOption(Option option, int questionId) {
        String sql = "INSERT INTO options (question_id, option_text, is_correct) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, questionId, option.getOptionText(), option.isCorrect());
    }

    public Option save(Option option) {
        final String sql = "INSERT INTO options (question_id, option_text, is_correct) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, option.getQuestionId());
            ps.setString(2, option.getOptionText());
            ps.setBoolean(3, option.isCorrect());
            return ps;
        }, keyHolder);

        option.setId(keyHolder.getKey().intValue());
        return option;
    }

    public List<Option> findByQuestionId(int questionId) {
        final String sql = "SELECT * FROM options WHERE question_id = ?";
        return jdbcTemplate.query(sql, new Object[]{questionId}, (rs, rowNum) -> Option.builder()
                .id(rs.getInt("id"))
                .questionId(rs.getInt("question_id"))
                .optionText(rs.getString("option_text"))
                .build());
    }

    public Option findCorrectOptionByQuestionId(int questionId) {
        final String sql = "SELECT * FROM options WHERE question_id = ? AND is_correct = TRUE";
        return jdbcTemplate.queryForObject(sql, new Object[]{questionId}, (rs, rowNum) ->
                Option.builder()
                        .id(rs.getInt("id"))
                        .questionId(rs.getInt("question_id"))
                        .optionText(rs.getString("option_text"))
                        .isCorrect(rs.getBoolean("is_correct"))
                        .build()
        );
    }
}
