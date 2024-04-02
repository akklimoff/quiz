package kg.attractor.quiz_project.controller;

import kg.attractor.quiz_project.dto.QuizDetailDto;
import kg.attractor.quiz_project.dto.QuizDto;
import kg.attractor.quiz_project.dto.QuizSummaryDto;
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
}
