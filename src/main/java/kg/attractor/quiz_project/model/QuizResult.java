package kg.attractor.quiz_project.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QuizResult {
    private int id;
    private String userUsername;
    private int quizId;
    private int score;
}
