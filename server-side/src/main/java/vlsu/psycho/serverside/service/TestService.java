package vlsu.psycho.serverside.service;

import vlsu.psycho.serverside.dto.test.TestDto;
import vlsu.psycho.serverside.dto.test.custom.AddCustomTestDto;
import vlsu.psycho.serverside.model.Test;

import java.util.UUID;

public interface TestService {
    TestDto getTest(UUID testExternalId, String languageCode);
    void save(Test test);
}
