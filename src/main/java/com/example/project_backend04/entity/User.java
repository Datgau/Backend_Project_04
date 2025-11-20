package com.example.project_backend04.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Username không cần unique nếu đăng nhập Facebook
    @Column(unique = true)
    private String username;

    // OAuth user không cần password
    @Column
    private String password;

    // Email Facebook có thể không có, nên cho phép null
    @Column(unique = true)
    private String email;

    @Column
    private String fullName;

    @Column
    private String phoneNumber;

    @Column
    private String avatar;

    @Column(length = 512)
    private String refreshToken;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createDate;

    @Column
    private LocalDateTime refreshTokenExpiryTime;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    // Một user có thể liên kết nhiều providers (Facebook, Google,…)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserProvider> providers;

    @PrePersist
    protected void onCreate() {
        this.createDate = LocalDateTime.now();
    }
}
