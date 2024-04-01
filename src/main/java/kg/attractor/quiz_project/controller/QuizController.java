package kg.attractor.quiz_project.controller;

import kg.attractor.quiz_project.dto.QuizDto;
import kg.attractor.quiz_project.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
