package vlsu.psycho.serverside.service;

import vlsu.psycho.serverside.dto.test.TestDto;

import java.util.UUID;

public interface TestService {
    TestDto getTest(UUID testExternalId, String languageCode);
}
