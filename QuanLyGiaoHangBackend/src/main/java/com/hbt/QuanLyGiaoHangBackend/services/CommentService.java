package com.hbt.QuanLyGiaoHangBackend.services;

import com.hbt.QuanLyGiaoHangBackend.exception.AppException;
import com.hbt.QuanLyGiaoHangBackend.exception.ErrorCode;
import com.hbt.QuanLyGiaoHangBackend.pojo.Comment;
import com.hbt.QuanLyGiaoHangBackend.pojo.User;
import com.hbt.QuanLyGiaoHangBackend.repositories.CommentRepository;
import com.hbt.QuanLyGiaoHangBackend.repositories.ShipperRepository;
import com.hbt.QuanLyGiaoHangBackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShipperRepository shipperRepository;

    public void addComment(Map<String,String> params){
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Comment c = new Comment();
        c.setCommentOfUser(params.get("comment"));
        c.setUserId(user);
        c.setShipperId(shipperRepository.findById(Long.parseLong(params.get("shipperId"))).orElse(null));
        commentRepository.save(c);
    }

}

