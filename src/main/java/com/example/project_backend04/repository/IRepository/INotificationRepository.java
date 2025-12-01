package com.example.project_backend04.repository.IRepository;

import com.example.project_backend04.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface INotificationRepository extends JpaRepository<Notification, Long> {
}
