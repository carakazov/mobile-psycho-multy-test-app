package vlsu.psycho.serverside.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vlsu.psycho.serverside.dto.ResultDto;
import vlsu.psycho.serverside.dto.test.taken.TakenTestDto;
import vlsu.psycho.serverside.dto.test.taken.TakenTestListDto;
import vlsu.psycho.serverside.dto.test.taken.TakenTestListItemDto;
import vlsu.psycho.serverside.service.TakenTestService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/takenTest")
public class TakenTestController {
    private final TakenTestService takenTestService;

    @PostMapping("/add")
    @PreAuthorize("permitAll()")
    public ResultDto add(@RequestBody TakenTestDto takenTestDto) {
        return takenTestService.save(takenTestDto);
    }

    @GetMapping("/list/{languageCode}")
    @Secured({"ROLE_CLIENT", "ROLE_PSYCHOLOGIST"})
    public TakenTestListDto getListOfTakenTests(@PathVariable String languageCode) {
        return takenTestService.getListOfTakenTests(languageCode);
    }
}
