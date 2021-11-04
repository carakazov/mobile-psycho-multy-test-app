package vlsu.psycho.serverside.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vlsu.psycho.serverside.model.Role;
import vlsu.psycho.serverside.model.RoleTitle;
import vlsu.psycho.serverside.repository.RoleRepository;
import vlsu.psycho.serverside.service.RoleService;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository repository;

    @Override
    public Role findByTitle(RoleTitle title) {
        return repository.findByTitle(title);
    }
}
