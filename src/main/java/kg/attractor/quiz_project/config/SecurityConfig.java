package kg.attractor.quiz_project.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final DataSource dataSource;

    private static final String USER_QUERY = "SELECT username, password, enabled FROM users WHERE username = ?";

    private static final String AUTHORITIES_QUERY = """
            select ua.user_username, a.role from user_authority ua, authorities a
            where ua.authority_id = a.id
            and ua.user_username = ?;
            """;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(USER_QUERY)
                .authoritiesByUsernameQuery(AUTHORITIES_QUERY)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults())
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize ->
                        authorize

                                .requestMatchers(HttpMethod.POST, "/api/register").permitAll()

                                .requestMatchers(HttpMethod.GET, "/api/quizzes/**").hasAuthority("DEFAULT")
                                .requestMatchers(HttpMethod.POST, "/api/quizzes/**").hasAuthority("DEFAULT")
                                .requestMatchers(HttpMethod.PUT, "/api/quizzes/**").hasAuthority("DEFAULT")
                                .requestMatchers(HttpMethod.DELETE, "/quizzes/**").hasAuthority("DEFAULT")

                                .requestMatchers(HttpMethod.GET, "/api/users/**").hasAuthority("DEFAULT")


                                .anyRequest().permitAll()
                );
        return http.build();
    }


}

