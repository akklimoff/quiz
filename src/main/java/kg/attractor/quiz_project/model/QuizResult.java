package kg.attractor.quiz_project.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QuizResult {
    private int id;
    private int user_id;
    private int quiz_id;
    private int score;
}
