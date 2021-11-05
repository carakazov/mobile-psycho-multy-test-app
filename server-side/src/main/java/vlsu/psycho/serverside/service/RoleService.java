package vlsu.psycho.serverside.service;

import vlsu.psycho.serverside.model.Role;
import vlsu.psycho.serverside.model.RoleTitle;

public interface RoleService {
    Role findByTitle(RoleTitle title);
}
