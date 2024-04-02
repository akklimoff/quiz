package kg.attractor.quiz_project.service.impl;

import kg.attractor.quiz_project.dao.OptionDao;
import kg.attractor.quiz_project.dao.QuestionDao;
import kg.attractor.quiz_project.dao.QuizDao;
import kg.attractor.quiz_project.dao.QuizResultDao;
import kg.attractor.quiz_project.dto.*;
import kg.attractor.quiz_project.model.Option;
import kg.attractor.quiz_project.model.Question;
import kg.attractor.quiz_project.model.Quiz;
import kg.attractor.quiz_project.model.QuizResult;
import kg.attractor.quiz_project.service.QuizService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {
    private final QuizDao quizDao;
    private final QuestionDao questionDao;
    private final OptionDao optionDao;
    private final QuizResultDao quizResultDao;

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
    @Override
    public List<QuizSummaryDto> getAllQuizzesSummary() {
        List<Quiz> quizzes = quizDao.findAll();
        List<QuizSummaryDto> summaries = new ArrayList<>();

        for (Quiz quiz : quizzes) {
            int questionCount = questionDao.countByQuizId(quiz.getId());
            summaries.add(new QuizSummaryDto(quiz.getTitle(), questionCount));
        }

        return summaries;
    }
    @Override
    public QuizDetailDto getQuizDetail(int quizId) {
        Quiz quiz = quizDao.findById(quizId);
        List<Question> questions = questionDao.findByQuizId(quizId);

        List<QuestionDto> questionDtos = questions.stream().map(question -> {
            List<Option> options = optionDao.findByQuestionId(question.getId());
            List<OptionDto> optionDtos = options.stream()
                    .map(option -> new OptionDto(option.getId(), option.getOptionText()))
                    .collect(Collectors.toList());
            return new QuestionDto(question.getId(), question.getQuestionText(), optionDtos);
        }).collect(Collectors.toList());

        return new QuizDetailDto(quiz.getId(), quiz.getTitle(), questionDtos);
    }

    @Override
    @Transactional
    public QuizResultDto solveQuiz(int quizId, QuizSubmissionDto submission, Authentication auth) {
        boolean quizExists = quizDao.existsById(quizId);
        if (!quizExists) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz with ID " + quizId + " not found");
        }
        int correctCount = 0;
        for (QuestionAnswerDto submittedAnswer : submission.getAnswers()) {
            Option correctOption = optionDao.findCorrectOptionByQuestionId(submittedAnswer.getQuestionId());
            if (correctOption != null && submittedAnswer.getOptionIds().contains(correctOption.getId())) {
                correctCount++;
            }
        }
        QuizResultDto result = QuizResultDto.builder()
                .quizId(quizId)
                .userUsername(auth.getName())
                .score(correctCount)
                .build();
        result = quizDao.saveQuizResult(result);

        return result;
    }

    @Override
    @Transactional
    public void rateQuiz(int quizId, String username, QuizRatingDto ratingDto) {
        Optional<QuizResult> quizResultOptional = quizResultDao.findByQuizIdAndUsername(quizId, username);
        QuizResult quizResult = quizResultOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz result not found."));
        quizResult.setRating(ratingDto.getRating());

        quizResultDao.save(quizResult);
    }

}
