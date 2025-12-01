package com.example.project_backend04.repository.IRepository;

import com.example.project_backend04.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPostRepository extends JpaRepository<Post, Long> {
}
