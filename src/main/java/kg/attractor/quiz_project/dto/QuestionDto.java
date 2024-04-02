package kg.attractor.quiz_project.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {
    private int id;
    List<OptionDto> options;
    @NotBlank(message = "Question text cannot be blank")
    private String questionText;
}
