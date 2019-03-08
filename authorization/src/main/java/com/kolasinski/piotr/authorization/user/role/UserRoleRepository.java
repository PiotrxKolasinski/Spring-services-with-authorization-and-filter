package com.kolasinski.piotr.authorization.user.role;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRoleRepository extends CrudRepository<UserRole, Long> {

    List<UserRole> findAllByUser_IdAndUser_Email(long user_id, String user_email);
}
