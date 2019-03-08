package com.kolasinski.piotr.authorization.role;

import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByType(RoleType type);
}
