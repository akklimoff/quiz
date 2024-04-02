package kg.attractor.quiz_project.controller;

import kg.attractor.quiz_project.dto.UserStatisticsDto;
import kg.attractor.quiz_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}/statistics")
    public ResponseEntity<UserStatisticsDto> getUserStatistics(@PathVariable String userId) {
        UserStatisticsDto statistics = userService.getUserStatistics(userId);
        return ResponseEntity.ok(statistics);
    }
}
