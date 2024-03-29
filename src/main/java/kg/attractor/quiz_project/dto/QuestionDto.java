package kg.attractor.quiz_project.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {
    private int id;
    private int quiz_id;
    @NotBlank(message = "Question text cannot be blank")
    private String question_text;
}
