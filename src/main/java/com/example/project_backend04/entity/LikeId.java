package com.example.project_backend04.entity;

import lombok.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LikeId implements Serializable {
    private Long user;
    private Long post;
}
