package kg.attractor.quiz_project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OptionDto {
    private int id;
    private int question_id;
    private String option_text;
    private boolean isCorrect;
}
