package com.hbt.QuanLyGiaoHangBackend.controllers;

import com.hbt.QuanLyGiaoHangBackend.dto.response.ShipperResponse;
import com.hbt.QuanLyGiaoHangBackend.dto.response.UserResponse;
import com.hbt.QuanLyGiaoHangBackend.pojo.User;
import com.hbt.QuanLyGiaoHangBackend.services.RateService;
import com.hbt.QuanLyGiaoHangBackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/api/")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RateService rateService;

    @Value("${PAGE_SIZE}")
    private int pageSize;

    @GetMapping("admin/getAllUsers/")
    public Page<User> getAllUsers(@RequestParam Map<String, String> params){
        params.put("pageSize", pageSize+"");
        return this.userService.getAllUsers(params);
    }

    @GetMapping("customer/getUser/{userId}")
    public User getUserByID(@PathVariable("userId") Long id){return this.userService.getUserByID(id);}

    @GetMapping(path = "current-user/", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<UserResponse> details(){
        return new ResponseEntity<>(this.userService.getCurrentUser(), HttpStatus.OK);
    }


}
