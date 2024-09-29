package com.hbt.QuanLyGiaoHangBackend.controllers;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.database.*;
import com.hbt.QuanLyGiaoHangBackend.component.JwtService;
import com.hbt.QuanLyGiaoHangBackend.dto.request.MessageRequest;
import com.hbt.QuanLyGiaoHangBackend.services.FirebaseAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class ChatController {

    @Autowired
    private FirebaseAuthService firebaseAuthService;

    @Autowired
    private JwtService jwtService;

    //dùng token để xác thực firebase
    @GetMapping("/customer/firebase-custom-token")
    public String getFirebaseCustomToken() throws FirebaseAuthException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        try {
            // Tạo custom token
            String customToken =  FirebaseAuth.getInstance().createCustomToken(authentication.getName());

            return customToken; // Trả về token cho client
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
            return "Error creating custom token: " + e.getMessage();
        }
    }
}