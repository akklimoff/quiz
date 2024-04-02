package kg.attractor.quiz_project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizResultDto {
    private int id;
    private String userUsername;
    private int quizId;
    private int score;
}
