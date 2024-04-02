package kg.attractor.quiz_project.service.impl;

import kg.attractor.quiz_project.dao.OptionDao;
import kg.attractor.quiz_project.dao.QuestionDao;
import kg.attractor.quiz_project.dao.QuizDao;
import kg.attractor.quiz_project.dto.OptionDto;
import kg.attractor.quiz_project.dto.QuestionDto;
import kg.attractor.quiz_project.dto.QuizDto;
import kg.attractor.quiz_project.model.Option;
import kg.attractor.quiz_project.model.Question;
import kg.attractor.quiz_project.model.Quiz;
import kg.attractor.quiz_project.service.QuizService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {
    private final QuizDao quizDao;
    private final QuestionDao questionDao;
    private final OptionDao optionDao;
//    @Override
//    public void createQuiz(QuizDto quizDto, Authentication auth) {
//        String userUsername = auth.getName();
//        Quiz quiz = Quiz.builder()
//                .title(quizDto.getTitle())
//                .description(quizDto.getDescription())
//                .creator_username(userUsername)
//                .build();
//        quizDao.createQuiz(quiz);
//    }

    @Override
    @Transactional
    public void createQuiz(QuizDto quizDto, Authentication auth) {
        Quiz quiz = Quiz.builder()
                .title(quizDto.getTitle())
                .creatorUsername(auth.getName())
                .description(quizDto.getDescription())
                .build();

        Quiz savedQuiz = quizDao.save(quiz);

        for (QuestionDto questionDto : quizDto.getQuestions()) {
            Question question = Question.builder()
                    .quizId(savedQuiz.getId())
                    .questionText(questionDto.getQuestionText())
                    .build();

            Question savedQuestion = questionDao.save(question);

            for (OptionDto optionDto : questionDto.getOptions()) {
                Option option = Option.builder()
                        .questionId(savedQuestion.getId())
                        .optionText(optionDto.getOptionText())
                        .isCorrect(optionDto.isCorrect())
                        .build();
                optionDao.save(option);
            }
        }
    }

}
