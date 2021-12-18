package vlsu.psycho.serverside.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vlsu.psycho.serverside.dto.test.taken.TakenTestDto;
import vlsu.psycho.serverside.service.TakenTestService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/takenTest")
public class TakenTestController {
    private final TakenTestService takenTestService;

    @PostMapping("/add")
    @Secured({"ROLE_CLIENT", "ROLE_PSYCHOLOGIST"})
    public String add(@RequestBody TakenTestDto takenTestDto) {
        return takenTestService.save(takenTestDto);
    }
}
