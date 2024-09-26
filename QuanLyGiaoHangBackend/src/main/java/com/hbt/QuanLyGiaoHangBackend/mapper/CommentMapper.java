package com.hbt.QuanLyGiaoHangBackend.mapper;

import com.hbt.QuanLyGiaoHangBackend.dto.response.CommentResponse;
import com.hbt.QuanLyGiaoHangBackend.dto.response.OrdersResponse;
import com.hbt.QuanLyGiaoHangBackend.pojo.Comment;
import com.hbt.QuanLyGiaoHangBackend.pojo.Product;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CommentMapper {


    CommentResponse toCommentResponse(Comment comment){
        CommentResponse o =new CommentResponse(comment.getId(), comment.getUserId().getUsername(), comment.getCommentOfUser());
        return o;
    }


    public List<CommentResponse> toCommentsResponseList(Set<Comment> comments){
        int size=0;
        if(comments!=null)size= comments.size();
        List<CommentResponse> list = new ArrayList(size);
        Iterator var3 = comments.iterator();

        while(var3.hasNext()) {
            Comment d = (Comment)var3.next();
            list.add(this.toCommentResponse(d));
        }
        Collections.reverse(list);
        return list;

    }

}


