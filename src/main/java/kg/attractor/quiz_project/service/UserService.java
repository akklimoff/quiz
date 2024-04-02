package kg.attractor.quiz_project.service;

import kg.attractor.quiz_project.dto.UserDto;
import kg.attractor.quiz_project.dto.UserStatisticsDto;

public interface UserService {
    void createUser(UserDto user);
    UserStatisticsDto getUserStatistics(String userId);
}
