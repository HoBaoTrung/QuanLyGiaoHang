package com.hbt.QuanLyGiaoHangBackend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String username;
    private String password;
    private String phone;
    private String email;
    private String cmnd;

    public RegisterRequest(String username,String pass,String phone,String mail){
        this.username=username;
        this.email=mail;
        this.password=pass;
        this.phone=phone;
    }

}