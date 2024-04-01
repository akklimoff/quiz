package kg.attractor.quiz_project.dao;

import kg.attractor.quiz_project.dto.UserDto;
import kg.attractor.quiz_project.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserDao {
    private final JdbcTemplate template;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public boolean isUserExistsByEmail(String email) {
        String sql = "select count(*) from users where email = ?";
        Integer count = template.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    public boolean isUserExistsByUsername(String username) {
        String sql = "select count(*) from users where username = ?";
        Integer count = template.queryForObject(sql, Integer.class, username);
        return count != null && count > 0;
    }

    public void createUser(UserDto user) {
        Optional<User> existingUserPhone = findByEmail(user.getEmail());
        Optional<User> existingUserUsername = findByUsername(user.getUsername());
        if (existingUserPhone.isPresent() && existingUserUsername.isPresent()) {
            throw new IllegalArgumentException("User with username " + user.getUsername() + " already exists.");
        }
        String sql = "insert into users (username, password, email, ENABLED) " +
                "values (:username, :password, :email, :enabled);";
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource()
                .addValue("username", user.getUsername())
                .addValue("password", user.getPassword())
                .addValue("email", user.getEmail())
                .addValue("enabled", true));

    }

    public Optional<User> findByEmail(String email) {
        String sql = "select * from users where email = ?";
        List<User> users = template.query(sql, new BeanPropertyRowMapper<>(User.class), email);
        return users.stream().findFirst();
    }

    public Optional<User> findByUsername(String username) {
        String sql = "select * from users where username = ?";
        List<User> users = template.query(sql, new BeanPropertyRowMapper<>(User.class), username);
        return users.stream().findFirst();
    }
}
