package kg.attractor.quiz_project.service;

import kg.attractor.quiz_project.dto.*;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface QuizService {
    void createQuiz(QuizDto quizDto, Authentication auth);
    List<QuizSummaryDto> getAllQuizzesSummary();
    QuizDetailDto getQuizDetail(int quizId);
    QuizResultDto solveQuiz(int quizId, QuizSubmissionDto submission, Authentication auth);
    void rateQuiz(int quizId, String username, QuizRatingDto ratingDto);
    LeaderboardDto getQuizLeaderboard(int quizId);
}
