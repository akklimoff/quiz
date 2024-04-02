package kg.attractor.quiz_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStatisticsDto {
    private int totalQuizzesTaken;
    private double averageScore;
}
