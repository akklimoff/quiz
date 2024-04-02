package kg.attractor.quiz_project.dao;

import kg.attractor.quiz_project.model.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Statement;

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
}