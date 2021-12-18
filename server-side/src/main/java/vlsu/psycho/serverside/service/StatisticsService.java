package vlsu.psycho.serverside.service;

import vlsu.psycho.serverside.dto.test.taken.TakenTestDetailsDto;
import vlsu.psycho.serverside.dto.test.taken.TakenTestTimeStatisticDto;

import java.util.UUID;

public interface StatisticsService {
    TakenTestTimeStatisticDto getResultsInTime(UUID testExternalId, String languageCode);
    TakenTestTimeStatisticDto getResultOfClient(UUID testExternalId, String languageCode, UUID clientExternalId);
}
