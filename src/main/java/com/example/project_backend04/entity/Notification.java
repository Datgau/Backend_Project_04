package com.example.project_backend04.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notifications", indexes = {
        @Index(name = "idx_notification_user", columnList = "user_id"),
        @Index(name = "idx_notification_created_at", columnList = "createdAt DESC"),
        @Index(name = "idx_notification_read", columnList = "isRead")
})
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Người nhận thông báo
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Người gây ra thông báo (like, comment, follow...)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_id")
    private User actor;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Column(columnDefinition = "NVARCHAR(500)")
    private String content;

    // ID của đối tượng liên quan (post_id, comment_id...)
    private Long relatedId;

    @Column(nullable = false)
    private Boolean isRead = false;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.isRead == null) {
            this.isRead = false;
        }
    }

    public enum NotificationType {
        LIKE,           // Ai đó thích bài viết
        COMMENT,        // Ai đó bình luận
        FOLLOW,         // Ai đó theo dõi
        MENTION,        // Ai đó nhắc đến
        REPLY,          // Ai đó trả lời comment
        STORY_VIEW,     // Ai đó xem story
        POST_TAG        // Ai đó tag trong bài viết
    }
}
