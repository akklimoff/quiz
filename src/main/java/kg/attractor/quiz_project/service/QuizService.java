package kg.attractor.quiz_project.service;

import kg.attractor.quiz_project.dto.QuizDetailDto;
import kg.attractor.quiz_project.dto.QuizDto;
import kg.attractor.quiz_project.dto.QuizSummaryDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface QuizService {
    void createQuiz(QuizDto quizDto, Authentication auth);
    List<QuizSummaryDto> getAllQuizzesSummary();
    QuizDetailDto getQuizDetail(int quizId);
}
