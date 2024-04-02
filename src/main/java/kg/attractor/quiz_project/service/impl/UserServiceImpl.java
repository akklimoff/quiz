package kg.attractor.quiz_project.service.impl;

import kg.attractor.quiz_project.dao.QuizResultDao;
import kg.attractor.quiz_project.dao.UserDao;
import kg.attractor.quiz_project.dto.UserDto;
import kg.attractor.quiz_project.dto.UserStatisticsDto;
import kg.attractor.quiz_project.model.QuizResult;
import kg.attractor.quiz_project.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final QuizResultDao quizResultDao;

    @Override
    public void createUser(UserDto user) {
        if (userDao.isUserExistsByEmail(user.getEmail())) {
            throw new NoSuchElementException("User with email " + user.getEmail() + " already exists.");
        }
        if (userDao.isUserExistsByUsername(user.getUsername())) {
            throw new NoSuchElementException("User with username " + user.getUsername() + " already exists.");
        }

        userDao.createUser(user);
    }

    public UserStatisticsDto getUserStatistics(String userId) {
        List<QuizResult> quizResults = quizResultDao.findByUserId(userId);

        int totalQuizzesTaken = quizResults.size();
        double averageScore = quizResults.stream()
                .mapToInt(QuizResult::getScore)
                .average()
                .orElse(0.0);

        return new UserStatisticsDto(totalQuizzesTaken, averageScore);
    }


}
