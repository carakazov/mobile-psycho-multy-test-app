package vlsu.psycho.serverside.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vlsu.psycho.serverside.dto.test.TestDto;
import vlsu.psycho.serverside.service.TestService;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    private final TestService testService;

    @GetMapping("{testId}/{language}")
    public TestDto getTest(@PathVariable UUID testId, @PathVariable String language) {
        return testService.getTest(testId, language);
    }
}
