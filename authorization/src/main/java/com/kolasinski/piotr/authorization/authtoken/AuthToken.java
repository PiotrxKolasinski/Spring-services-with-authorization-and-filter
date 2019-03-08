package com.kolasinski.piotr.authorization.authtoken;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tb_auth_token")
public class AuthToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auth_token_id")
    @JsonView(AuthTokenViews.Public.class)
    private long id;

    @Column(name = "auth_token_type")
    @JsonView(AuthTokenViews.Internal.class)
    private AuthTokenType type;

    @Column(name = "auth_token_user_email")
    @JsonView(AuthTokenViews.Internal.class)
    private String userEmail;

    @Column(name = "auth_token_token")
    @JsonView(AuthTokenViews.Internal.class)
    private String token;

    @Column(name = "auth_token_create_date")
    @JsonView(AuthTokenViews.Internal.class)
    private Instant createDate;
}
