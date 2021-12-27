package vlsu.psycho.serverside.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vlsu.psycho.serverside.dto.test.ShowTestDto;
import vlsu.psycho.serverside.dto.test.TestDto;
import vlsu.psycho.serverside.service.TestService;
import vlsu.psycho.serverside.utils.jwt.JwtProvider;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    private final TestService testService;
    private final JwtProvider provider;

    @GetMapping("{testId}/{language}")
    @Secured({"ROLE_CLIENT", "ROLE_PSYCHOLOGIST"})
    public TestDto getTest(@PathVariable UUID testId, @PathVariable String language) {
        return testService.getTest(testId, language);
    }

    @GetMapping("default/{language}")
    @PreAuthorize("permitAll()")
    public TestDto getDefaultTest(@PathVariable String language) {
        return testService.getDefaultTest(language);
    }

    @GetMapping("/list/{languageCode}")
    @PreAuthorize("permitAll()")
    public List<ShowTestDto> getAllAvailableTests(@PathVariable String languageCode) {
        return testService.getAvailableTests(languageCode);
    }
}
