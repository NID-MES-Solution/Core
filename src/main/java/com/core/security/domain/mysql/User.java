package com.core.security.domain.mysql;

import com.core.security.domain.type.ESecurityProvider;
import com.core.security.domain.type.ESecurityRole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_accounts_serial_id",
                        columnNames = {"serial_id"}
                )
        }
)
@DynamicUpdate
public class User {

    /* -------------------------------------------- */
    /* Default Column ----------------------------- */
    /* -------------------------------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    /* -------------------------------------------- */
    /* Security Column ---------------------------- */
    /* -------------------------------------------- */
    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false, updatable = false)
    private ESecurityProvider provider;

    @Column(name = "serial_id", length = 20, nullable = false, updatable = false)
    private String serialId;

    @Column(name = "password", length = 320, nullable = false)
    private String password;

    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private ESecurityRole role;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public User(
            String serialId,
            String password,
            String name,
            ESecurityRole role

    ) {
        this.provider = ESecurityProvider.DEFAULT;
        this.serialId = serialId;
        this.password = password;
        this.name = name;
        this.role = role;
        this.createdAt = LocalDateTime.now();
    }

}
