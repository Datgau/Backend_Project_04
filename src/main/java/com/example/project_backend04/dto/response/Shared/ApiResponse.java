package com.example.project_backend04.dto.response.Shared;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;
    private int status;

    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
