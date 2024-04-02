package kg.attractor.quiz_project.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Question {
    private int id;
    private int quizId;
    private String questionText;
}
