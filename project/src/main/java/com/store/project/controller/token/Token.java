package com.store.project.controller.token;

import com.store.project.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
//@Table(name = "token")
public class Token {
    @Id
    @Column(name="id")
    @GeneratedValue
    public Integer id;

    @Column(name="token", unique = true)
    public String token;

    @Enumerated(EnumType.STRING)
    public TokenType tokenType = TokenType.BEARER;

    @Column(name = "revoked")
    public boolean revoked;

    @Column(name = "expired")
    public boolean expired;

   @Column(name="date_of_creation")
//    ,  nullable = false)
   @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name="date_of_expiration")
//    ,  nullable = false)
    private Date expiresAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;



}
