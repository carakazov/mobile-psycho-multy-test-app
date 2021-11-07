package vlsu.psycho.serverside.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vlsu.psycho.serverside.dto.test.custom.AddCustomTestDto;
import vlsu.psycho.serverside.service.CustomTestService;

import javax.validation.Valid;

@RestController
@RequestMapping("/custom")
@RequiredArgsConstructor
public class CustomTestController {
    private final CustomTestService customTestService;

    @PostMapping
    @Secured("ROLE_PSYCHOLOGIST")
    public void createCustomTest(@RequestBody @Valid AddCustomTestDto addCustomTestDto) {
        customTestService.createCustomTest(addCustomTestDto);
    }
}
