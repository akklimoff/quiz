package kg.attractor.quiz_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizDetailDto {
    private int id;
    private String title;
    private List<QuestionDto> questions;
}
