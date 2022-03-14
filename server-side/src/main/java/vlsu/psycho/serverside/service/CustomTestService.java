package vlsu.psycho.serverside.service;

import vlsu.psycho.serverside.dto.test.custom.AddCustomTestDto;
import vlsu.psycho.serverside.model.CustomTest;

import java.util.List;
import java.util.UUID;

public interface CustomTestService {
    void createCustomTest(AddCustomTestDto addCustomTestDto);
    List<CustomTest> findByAuthorId(UUID externalId);
}
