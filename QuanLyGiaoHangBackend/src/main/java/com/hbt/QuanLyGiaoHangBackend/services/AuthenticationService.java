package com.hbt.QuanLyGiaoHangBackend.services;

import com.hbt.QuanLyGiaoHangBackend.component.JwtService;
import com.hbt.QuanLyGiaoHangBackend.dto.request.RegisterRequest;
import com.hbt.QuanLyGiaoHangBackend.exception.AppException;
import com.hbt.QuanLyGiaoHangBackend.exception.ErrorCode;
import com.hbt.QuanLyGiaoHangBackend.dto.request.AuthenticationRequest;
import com.hbt.QuanLyGiaoHangBackend.dto.response.AuthenticationResponse;
import com.hbt.QuanLyGiaoHangBackend.pojo.User;
import com.hbt.QuanLyGiaoHangBackend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthenticationResponse login(AuthenticationRequest request) throws Exception {
        User u = userRepository.findByUsername(request.getUsername()).orElseThrow(
                () ->   new AppException(ErrorCode.USER_NOT_EXISTED)
        );

        if(!u.getActive()){return null;}

        boolean auth = passwordEncoder.matches(request.getPassword(), u.getPassword());
        if(!auth)
        {throw new Exception("Sai mật khẩu.");}

        String jwtToken = jwtService.generateTokenLogin(u);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}