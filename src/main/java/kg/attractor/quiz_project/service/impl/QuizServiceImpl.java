package kg.attractor.quiz_project.service.impl;

import kg.attractor.quiz_project.dao.QuizDao;
import kg.attractor.quiz_project.dto.QuizDto;
import kg.attractor.quiz_project.model.Quiz;
import kg.attractor.quiz_project.service.QuizService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {
    private final QuizDao quizDao;
    @Override
    public void createQuiz(QuizDto quizDto, Authentication auth) {
        String userUsername = auth.getName();
        Quiz quiz = Quiz.builder()
                .title(quizDto.getTitle())
                .description(quizDto.getDescription())
                .creator_username(userUsername)
                .build();
        quizDao.createQuiz(quiz);
    }
}
