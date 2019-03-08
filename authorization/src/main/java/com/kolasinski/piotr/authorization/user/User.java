package com.kolasinski.piotr.authorization.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.kolasinski.piotr.authorization.user.role.UserRole;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @JsonView(UserViews.Public.class)
    private long id;

    @Column(name = "user_email")
    @JsonView(UserViews.Public.class)
    private String email;

    @Column(name = "user_password")
    @JsonView(UserViews.Internal.class)
    private String password;

    @Column(name = "user_active")
    @JsonView(UserViews.Internal.class)
    private boolean active;

    @Column(name = "user_create_date")
    @JsonView(UserViews.Internal.class)
    private Instant createDate;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    @JsonView(UserViews.Public.class)
    private List<UserRole> userRoles = new ArrayList<>();
}