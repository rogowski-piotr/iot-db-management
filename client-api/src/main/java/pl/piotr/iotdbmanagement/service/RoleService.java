package pl.piotr.iotdbmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.piotr.iotdbmanagement.role.Role;
import pl.piotr.iotdbmanagement.role.RoleRepository;

@Service
public class RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getRoleForUser() {
        return roleRepository.findRoleByName("USER").orElseThrow();
    }

}