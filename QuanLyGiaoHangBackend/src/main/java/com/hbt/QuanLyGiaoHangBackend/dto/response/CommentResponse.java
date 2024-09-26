package com.hbt.QuanLyGiaoHangBackend.dto.response;

import com.hbt.QuanLyGiaoHangBackend.pojo.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentResponse {
     Long id;
     String username;
     String comment;

}