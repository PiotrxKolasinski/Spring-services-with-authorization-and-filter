package com.kolasinski.piotr.services.user.role;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.kolasinski.piotr.services.role.Role;
import com.kolasinski.piotr.services.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tb_user_role")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_role_id")
    @JsonView(UserRoleViews.Public.class)
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_role_role_id")
    @JsonView(UserRoleViews.Public.class)
    private Role role;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_role_user_id")
    @JsonBackReference
    @JsonView(UserRoleViews.Public.class)
    private User user;
}
