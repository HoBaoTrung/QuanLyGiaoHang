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
import com.hbt.QuanLyGiaoHangBackend.pojo.*;
import com.hbt.QuanLyGiaoHangBackend.repositories.*;
import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.logging.Logger;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    @Autowired
    private VerificationTokenRepository tokenRepository;

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

    @Autowired
    private EmailService emailService;

    //chỉ cho phép user (và admin) lấy thông tin của user đó
    @PostAuthorize("returnObject.username == authentication.name or hasRole('ADMIN')")
    public User getUserByID(long id) {
        return userRepository.findById(id).orElse(null);
    }

    @PreAuthorize("returnObject!=null && returnObject.username == authentication.name or hasRole('ADMIN')")
    public void deleteUserByID(long id) {
         userRepository.delete(getUserByID(id));
    }


    public void upadteUserByID( Map<String, String> params) {
        System.out.println(params);
        User u = this.userRepository.findById(getCurrentUser().getId()).orElse(null);
        String email=params.get("email"), phone=params.get("phone"), password=params.get("password");
        Map<String, String> errors = new HashMap<>();

        if(email!=null && !email.isEmpty()){
            if (userRepository.existsByEmail(email)) {
                errors.put("email", "Email đã tồn tại");
            }
            else{u.setEmail(email);}
        }

        if(phone!=null && !phone.isEmpty()){
            if (userRepository.existsByPhone(phone)) {
                errors.put("phone", "Số điện thoại đã tồn tại");
            } else {u.setPhone(phone);}

        }

        if(password!=null && !password.isEmpty()){
            u.setPassword(passwordEncoder.encode(password));
        }
        if (!errors.isEmpty()) {
            throw new DuplicateFieldException(errors);
        }
//        userRepository.save(u);
    }

    public Page<User> getAllUsers(Map<String, String> params) {
        Integer page = Integer.parseInt(params.get("page"));
        Integer pageSize = Integer.parseInt(params.get("pageSize"));
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        return userRepository.findAll(pageable);
    }

    public User getCurrentUserByUserName(){
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        return userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }

    public UserResponse getCurrentUser() {
         UserResponse ur = userMapper.toUserResponse(getCurrentUserByUserName());
        return ur;
    }

    public User getUserByUsername(String username) throws UsernameNotFoundException {
        User u = this.userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return u;
    }

    public ShipperResponse getShipperInfoById(long id) {
        Shipper s = this.shipperRepo.findById(id).orElse(null);
        return shipperMapper.toShipperResponse(s);
    }

    public List<?> getCommentByShipperID(Long shipperId) {
        Shipper shipper = shipperRepo.findById(shipperId)
                .orElseThrow(() -> new RuntimeException("Shipper không tồn tại"));
        return commentMapper.toCommentsResponseList(shipper.getCommentSet());
    }

    public UserResponse createUser(RegisterRequest request, MultipartFile avatar) throws MessagingException {
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
        user.setActive(false);
        if (avatar != null && !avatar.isEmpty()) {
            try {
                Map res = this.cloudinary.uploader().upload(avatar.getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                user.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            user.setAvatar("https://mdbootstrap.com/img/Photos/Others/placeholder-avatar.jpg");
        }

        HashSet<Role> roles = new HashSet<>();
        roleRepository.findById(PredefinedRole.USER_ROLE).ifPresent(roles::add);
        Shipper s = null;
        try {
            if (request.getCmnd() != null) {

                if (shipperRepo.existsByCmnd(request.getCmnd())) {
                    errors.put("cmnd", "CMND đã tồn tại");
                } else {
                    s = new Shipper();
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
            if (s != null)
                shipperRepo.save(s);

        } catch (DataIntegrityViolationException e) {
            System.out.println("Có lỗi về ràng buộc dữ liệu: " + e.getMessage());
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(token, user);
        tokenRepository.save(verificationToken);
        String verificationLink = "http://localhost:8080/QuanLyGiaoHang/api/auth/verify?token=" + token;

        String content = "<!DOCTYPE html>" +
                "<html lang=\"en\">" +
                "<head>" +
                "    <meta charset=\"UTF-8\">" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "    <style>" +
                "        .button {\n" +
                "  border: none;\n" +
                "  color: white;\n" +
                "  padding: 16px 32px;\n" +
                "  text-align: center;\n" +
                "  text-decoration: none;\n" +
                "  display: inline-block;\n" +
                "  font-size: 16px;\n" +
                "  margin: 4px 2px;\n" +
                "  transition-duration: 0.4s;\n" +
                "  cursor: pointer;\n" +
                "}\n" +
                ".button1 {\n" +
                "  background-color: white; \n" +
                "  color: black; \n" +
                "  border: 2px solid #04AA6D;\n" +
                "}\n" +
                ".button1:hover {\n" +
                "  background-color: #04AA6D;\n" +
                "  color: white;\n" +
                "}\n" +

                "    </style>" +
                "</head>" +
                "<body>" +
                "<h1 style='color:green;'>XÁC NHẬN TÀI KHOẢN CỦA BẠN</h1>" +
                "<div><a href=\"" + verificationLink + " \" class=\"button button1\">Xác nhận</a></div>" +
                "</body>" +
                "</html>";

        emailService.sendHtmlEmail(user.getEmail(), "Xác nhận tài khoản", content);

        return userMapper.toUserResponse(user);
    }

    public boolean verifyUser(String token) {
        Optional<VerificationToken> verificationToken = tokenRepository.findByToken(token);

        if (verificationToken.isPresent()) {
            VerificationToken tokenEntity = verificationToken.get();

            // Kiểm tra thời gian hết hạn
            if (tokenEntity.getExpiryDate().isBefore(LocalDateTime.now())) {
                return false; // Token đã hết hạn
            }

            User user = tokenEntity.getUser();
            user.setActive(true);
            userRepository.save(user);

            // Xoá token sau khi xác thực thành công
            tokenRepository.delete(tokenEntity);
            return true;
        }

        return false;
    }

    // Xóa tài khoản chưa xác nhận sau 1 phút
    public void deleteExpiredTokens() {
        LocalDateTime now = LocalDateTime.now();
        tokenRepository.deleteByExpiryDateBefore(now);
    }

    @Scheduled(fixedRate = 5000 * 100000) // Chạy mỗi 500000 giây
    public void removeExpiredAccounts() {
        // Xóa tất cả các token hết hạn
        deleteExpiredTokens();

        // Sau khi token bị xóa, xóa luôn tài khoản chưa kích hoạt
        userRepository.deleteAll(
                userRepository.findAll().stream()
                        .filter(user -> !user.getActive()) // Tìm tài khoản chưa kích hoạt
                        .collect(Collectors.toList())
        );
    }
}
