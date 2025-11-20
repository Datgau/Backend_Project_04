package com.example.project_backend04.repository.IRepository;

import com.example.project_backend04.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}