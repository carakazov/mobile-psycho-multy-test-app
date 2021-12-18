package vlsu.psycho.serverside.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import vlsu.psycho.serverside.dto.test.taken.TakenTestTimeStatisticDto;
import vlsu.psycho.serverside.service.StatisticsService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/statistics")
public class StatisticsController {
    private final StatisticsService statisticsService;

    @GetMapping("{testExternalId}/{languageCode}")
    @Secured({"ROLE_CLIENT", "ROLE_PSYCHOLOGIST"})
    public TakenTestTimeStatisticDto getTestResultsInTime(
            @PathVariable(name = "testExternalId") UUID testExternalId,
            @PathVariable(name = "languageCode") String languageCode
    ) {
        return statisticsService.getResultsInTime(testExternalId, languageCode);
    }

    @GetMapping("{testExternalId}/{languageCode}/{clientId}")
    @Secured({"ROLE_CLIENT", "ROLE_PSYCHOLOGIST"})
    public TakenTestTimeStatisticDto getTestResultsInTime(
            @PathVariable(name = "testExternalId") UUID testExternalId,
            @PathVariable(name = "languageCode") String languageCode,
            @PathVariable(name = "clientId") UUID clientId
    ) {
        return statisticsService.getResultOfClient(testExternalId, languageCode, clientId);
    }

}
