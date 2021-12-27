package vlsu.psycho.serverside.service;

import vlsu.psycho.serverside.dto.ResultDto;
import vlsu.psycho.serverside.dto.test.taken.TakenTestDto;
import vlsu.psycho.serverside.dto.test.taken.TakenTestListDto;
import vlsu.psycho.serverside.model.TakenTest;

import java.util.List;
import java.util.UUID;

public interface TakenTestService {
    ResultDto save(TakenTestDto takenTestDto);
    List<TakenTest> getTestsOfClient(UUID clientExternalId);
    boolean existsByExternalId(UUID takenTestExternalId);
    TakenTest getByExternalId(UUID takenTestExternalId);
    TakenTestListDto getListOfTakenTests(String languageCode);
}
