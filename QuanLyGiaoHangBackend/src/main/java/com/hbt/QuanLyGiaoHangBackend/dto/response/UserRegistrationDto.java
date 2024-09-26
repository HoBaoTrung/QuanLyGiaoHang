package com.hbt.QuanLyGiaoHangBackend.dto.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRegistrationDto {

    private String username;
    private String email;
    private String phone;
    private String cmnd;

}