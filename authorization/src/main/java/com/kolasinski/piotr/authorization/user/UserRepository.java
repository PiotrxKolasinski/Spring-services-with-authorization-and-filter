package com.kolasinski.piotr.authorization.user;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findById(long id);

    User findByEmail(String email);

    void deleteByIdAndEmail(long id, String email);
}
