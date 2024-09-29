package com.hbt.QuanLyGiaoHangBackend.controllers;

import com.hbt.QuanLyGiaoHangBackend.dto.request.AuthenticationRequest;
import com.hbt.QuanLyGiaoHangBackend.dto.request.RegisterRequest;
import com.hbt.QuanLyGiaoHangBackend.dto.response.AuthenticationResponse;
import com.hbt.QuanLyGiaoHangBackend.exception.DuplicateFieldException;
import com.hbt.QuanLyGiaoHangBackend.services.AuthenticationService;
import com.hbt.QuanLyGiaoHangBackend.services.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
        AuthenticationResponse response =null;
        try {
            response =  authService.login(request);
        }
        catch (Exception e){return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);}

        if(response==null) return new ResponseEntity<>("Tài khoản chưa xác nhận",HttpStatus.BAD_REQUEST);
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
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }


    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyAccount(@RequestParam("token") String token, HttpServletResponse response) throws IOException {
        boolean verified = userService.verifyUser(token);
        if (verified) {
            // Redirect to login with success status
            response.sendRedirect("http://localhost:3000/login?status=success");
        } else {
            // Redirect to login with fail status
            response.sendRedirect("http://localhost:3000/login?status=fail");
        }
        return ResponseEntity.ok().build();
    }

}
