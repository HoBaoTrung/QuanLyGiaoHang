package com.hbt.QuanLyGiaoHangBackend.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.hbt.QuanLyGiaoHangBackend.constant.PredefinedRole;
import com.hbt.QuanLyGiaoHangBackend.dto.request.RegisterRequest;
import com.hbt.QuanLyGiaoHangBackend.dto.response.ShipperResponse;
import com.hbt.QuanLyGiaoHangBackend.dto.response.UserResponse;
import com.hbt.QuanLyGiaoHangBackend.exception.AppException;
import com.hbt.QuanLyGiaoHangBackend.exception.DuplicateFieldException;
import com.hbt.QuanLyGiaoHangBackend.exception.ErrorCode;
import com.hbt.QuanLyGiaoHangBackend.mapper.CommentMapper;
import com.hbt.QuanLyGiaoHangBackend.mapper.ShipperMapper;
import com.hbt.QuanLyGiaoHangBackend.mapper.UserMapper;
import com.hbt.QuanLyGiaoHangBackend.pojo.Comment;
import com.hbt.QuanLyGiaoHangBackend.pojo.Role;
import com.hbt.QuanLyGiaoHangBackend.pojo.Shipper;
import com.hbt.QuanLyGiaoHangBackend.pojo.User;
import com.hbt.QuanLyGiaoHangBackend.repositories.CommentRepository;
import com.hbt.QuanLyGiaoHangBackend.repositories.RoleRepository;
import com.hbt.QuanLyGiaoHangBackend.repositories.ShipperRepository;
import com.hbt.QuanLyGiaoHangBackend.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.logging.Logger;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserMapper userMapper;

    @Autowired
    ShipperMapper shipperMapper;

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    ShipperRepository shipperRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private Cloudinary cloudinary;



    //chỉ cho phép user (và admin) lấy thông tin của user đó
    @PostAuthorize("returnObject.username == authentication.name or hasRole('ADMIN')")
    public User getUserByID(long id) {
        return userRepository.findById(id).orElse(null);
    }

    public Page<User> getAllUsers(Map<String, String> params) {
        Integer page = Integer.parseInt(params.get("page"));
        Integer pageSize = Integer.parseInt(params.get("pageSize"));
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        return userRepository.findAll(pageable);
    }

    public UserResponse getCurrentUser() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));


        UserResponse ur = userMapper.toUserResponse(user);

        return ur;
    }

    public User getUserByUsername(String username) throws UsernameNotFoundException {
        User u = this.userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return u;
    }

    public ShipperResponse getShipperInfoById(long id){
        Shipper s = this.shipperRepo.findById(id).orElse(null);
        return shipperMapper.toShipperResponse(s);
    }

    public List<?> getCommentByShipperID(Long shipperId){
        Shipper shipper = shipperRepo.findById(shipperId)
                .orElseThrow(() -> new RuntimeException("Shipper không tồn tại"));
        return commentMapper.toCommentsResponseList(shipper.getCommentSet());
    }

    public UserResponse createUser(RegisterRequest request, MultipartFile avatar) {
        Map<String, String> errors = new HashMap<>();
        if (userRepository.existsByEmail(request.getEmail())) {
            errors.put("email", "Email đã tồn tại");
        }

        if (userRepository.existsByPhone(request.getPhone())) {
            errors.put("phone", "Số điện thoại đã tồn tại");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            errors.put("username", "Tên người dùng đã tồn tại");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPhone(request.getPhone());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        if (avatar != null && !avatar.isEmpty()) {
            try {
                Map res = this.cloudinary.uploader().upload(avatar.getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                user.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{user.setAvatar("https://mdbootstrap.com/img/Photos/Others/placeholder-avatar.jpg");}

        HashSet<Role> roles = new HashSet<>();
        roleRepository.findById(PredefinedRole.USER_ROLE).ifPresent(roles::add);
        Shipper s = null;
        try {
            if (request.getCmnd() != null) {

                if (shipperRepo.existsByCmnd(request.getCmnd())) {
                    errors.put("cmnd", "CMND đã tồn tại");
                }
                else{s = new Shipper();
                    s.setCmnd(request.getCmnd());
                    s.setUser(user);
                    s.setActive(false);

                    roleRepository.findById(PredefinedRole.SHIPPER_ROLE).ifPresent(roles::add);
                }

            }

            if (!errors.isEmpty()) {
                throw new DuplicateFieldException(errors);
            }

            // Tiếp tục quá trình đăng ký nếu không có lỗi
            user.setRoles(roles);
            user = userRepository.save(user);
            if(s!=null)
            shipperRepo.save(s);

        } catch (DataIntegrityViolationException e) {
            System.out.println("Có lỗi về ràng buộc dữ liệu: " + e.getMessage());
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        return userMapper.toUserResponse(user);
    }
}
