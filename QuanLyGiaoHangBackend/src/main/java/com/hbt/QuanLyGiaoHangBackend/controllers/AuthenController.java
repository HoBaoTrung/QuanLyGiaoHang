package com.hbt.QuanLyGiaoHangBackend.controllers;

import com.hbt.QuanLyGiaoHangBackend.dto.request.AuthenticationRequest;
import com.hbt.QuanLyGiaoHangBackend.dto.request.RegisterRequest;
import com.hbt.QuanLyGiaoHangBackend.dto.response.AuthenticationResponse;
import com.hbt.QuanLyGiaoHangBackend.exception.DuplicateFieldException;
import com.hbt.QuanLyGiaoHangBackend.services.AuthenticationService;
import com.hbt.QuanLyGiaoHangBackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/auth/")
public class AuthenController {

    @Autowired
    private AuthenticationService authService;

    @Autowired
    private UserService userService;

    @PostMapping("login/")
    @CrossOrigin
    public ResponseEntity<String> login(@RequestBody AuthenticationRequest request)
    {
        AuthenticationResponse response = authService.login(request);

        return new ResponseEntity<>(response.getToken(), HttpStatus.OK);
    }

    @PostMapping("register/")
    @CrossOrigin
    public ResponseEntity<?> register(  @RequestParam Map<String, String> params
            ,  @RequestParam(required = false) MultipartFile avatar)
    {
        RegisterRequest request = new RegisterRequest(params.get("username"),
                params.get("password"), params.get("phone"), params.get("email"));
        if (params.get("cmnd") != null && !params.get("cmnd").isEmpty())
            request.setCmnd(params.get("cmnd"));

        try {
            this.userService.createUser(request, avatar);

            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DuplicateFieldException ex) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getErrors());
        }


    }

}
