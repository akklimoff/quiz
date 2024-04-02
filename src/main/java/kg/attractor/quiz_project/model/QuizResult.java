package kg.attractor.quiz_project.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizResult {
    private int id;
    private String userUsername;
    private int quizId;
    private int score;
    private Integer rating;
}
