package com.kolasinski.piotr.services.user;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);

    User findById(long id);

    void deleteByIdAndEmail(long id, String email);
}
