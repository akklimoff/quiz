package kg.attractor.quiz_project.service;

import kg.attractor.quiz_project.dto.QuizDto;
import org.springframework.security.core.Authentication;

public interface QuizService {
    void createQuiz(QuizDto quizDto, Authentication auth);
}
