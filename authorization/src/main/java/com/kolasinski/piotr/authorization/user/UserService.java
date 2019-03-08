package com.kolasinski.piotr.authorization.user;

import com.kolasinski.piotr.authorization.auth.AuthRegistration;
import com.kolasinski.piotr.authorization.role.RoleService;
import com.kolasinski.piotr.authorization.role.RoleType;
import com.kolasinski.piotr.authorization.user.role.UserRole;
import com.kolasinski.piotr.authorization.user.role.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


@Service
public class UserService {
    private final static Logger logger = Logger.getLogger(UserService.class.getName());

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;
    private final RoleService roleService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserRoleRepository userRoleRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
        this.roleService = roleService;
    }

    public User findOneByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findOne(long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public User save(final AuthRegistration authRegistration) {
        User user = userRepository.save(createUserToSave(authRegistration));
        userRoleRepository.save(createUserRoleToSave(user));
        logger.info("[UserService] save user account with email: " + user.getEmail());
        return user;
    }

    private User createUserToSave(final AuthRegistration authRegistration) {
        String password = passwordEncoder.encode(authRegistration.getPassword());
        Instant currentTime = Instant.now();
        return User.builder()
                .email(authRegistration.getEmail())
                .password(password)
                .active(false)
                .createDate(currentTime)
                .userRoles(new ArrayList<>())
                .build();
    }

    private UserRole createUserRoleToSave(User user) {
        return UserRole.builder()
                .role(roleService.findOneByType(RoleType.ROLE_USER))
                .user(user)
                .build();
    }

    public void activateUserAccount(final long id) {
        User user = userRepository.findById(id);
        user.setActive(true);
        userRepository.save(user);
        logger.info("[UserService] active user account with email: " + user.getEmail());
    }

    @Transactional
    public void deleteByIdAndEmail(final long id, final String email) {
        List<UserRole> userRoles = userRoleRepository.findAllByUser_IdAndUser_Email(id, email);
        deleteUserRoles(userRoles);
        userRepository.deleteByIdAndEmail(id, email);
        logger.info("[UserService] delete user account with email: " + email);
    }

    private void deleteUserRoles(List<UserRole> userRoles) {
        userRoles.forEach(userRole -> {
            userRoleRepository.deleteById(userRole.getId());
        });
    }
}
