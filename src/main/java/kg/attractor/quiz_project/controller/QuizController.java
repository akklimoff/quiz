package kg.attractor.quiz_project.controller;

import kg.attractor.quiz_project.dto.*;
import kg.attractor.quiz_project.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/quizzes")
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;

    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody QuizDto quizDto, Authentication auth) {
        quizService.createQuiz(quizDto, auth);
        return ResponseEntity.ok("Successfully created");
    }

    @GetMapping
    public ResponseEntity<List<QuizSummaryDto>> getAllQuizzes() {
        List<QuizSummaryDto> quizzes = quizService.getAllQuizzesSummary();
        return ResponseEntity.ok(quizzes);
    }

    @GetMapping("/{quizId}")
    public ResponseEntity<QuizDetailDto> getQuizDetail(@PathVariable int quizId) {
        QuizDetailDto quizDetail = quizService.getQuizDetail(quizId);
        return ResponseEntity.ok(quizDetail);
    }

    @PostMapping("/{quizId}/solve")
    public ResponseEntity<?> solveQuiz(@PathVariable int quizId, @RequestBody QuizSubmissionDto submission, Authentication auth) {
        QuizResultDto result = quizService.solveQuiz(quizId, submission, auth);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{quizId}/rate")
    public ResponseEntity<?> rateQuiz(@PathVariable int quizId, @RequestBody QuizRatingDto ratingDto, Authentication auth) {
        String username = auth.getName();
        quizService.rateQuiz(quizId, username, ratingDto);
        return ResponseEntity.ok("Rated");
    }

    @GetMapping("/{quizId}/leaderboard")
    public ResponseEntity<LeaderboardDto> getQuizLeaderboard(@PathVariable int quizId) {
        LeaderboardDto leaderboard = quizService.getQuizLeaderboard(quizId);
        return ResponseEntity.ok(leaderboard);
    }

}
