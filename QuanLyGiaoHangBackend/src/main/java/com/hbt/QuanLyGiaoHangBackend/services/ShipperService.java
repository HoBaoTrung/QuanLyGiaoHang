package com.hbt.QuanLyGiaoHangBackend.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.hbt.QuanLyGiaoHangBackend.Specification.ShipperSpecifications;
import com.hbt.QuanLyGiaoHangBackend.constant.PredefinedRole;
import com.hbt.QuanLyGiaoHangBackend.exception.AppException;
import com.hbt.QuanLyGiaoHangBackend.exception.DuplicateFieldException;
import com.hbt.QuanLyGiaoHangBackend.exception.ErrorCode;
import com.hbt.QuanLyGiaoHangBackend.pojo.Role;
import com.hbt.QuanLyGiaoHangBackend.pojo.Shipper;
import com.hbt.QuanLyGiaoHangBackend.pojo.User;
import com.hbt.QuanLyGiaoHangBackend.repositories.RoleRepository;
import com.hbt.QuanLyGiaoHangBackend.repositories.ShipperRepository;
import com.hbt.QuanLyGiaoHangBackend.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShipperService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ShipperRepository shipperRepository;

    @Autowired
    private Cloudinary cloudinary;

    public void updateActiveStatus(Long id, boolean active) {
        Shipper shipper = shipperRepository.findById(id).orElse(null);
        shipper.setActive(active);
        shipperRepository.save(shipper);
    }

    public Page<Shipper> getAllShipper( Map<String, String> params){

        Integer page = Integer.parseInt(params.get("page"));
        Integer pageSize = Integer.parseInt(params.get("pageSize"));
        boolean isActive = Boolean.parseBoolean((params.get("active")));
        Specification<Shipper> specShipper = Specification.where(null);
        specShipper=specShipper.and(ShipperSpecifications.shipperActive(isActive));
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "id"));

        return shipperRepository.findAll(specShipper,pageable);
    }

    public void addOrUpdateShipper(String cmnd, MultipartFile avatar, boolean isUpdate) {
        Map<String, String> errors = new HashMap<>();

        if(cmnd!=null && !cmnd.isEmpty()){
        if (shipperRepository.existsByCmnd(cmnd)) {
            errors.put("cmnd", "CMND/CCCD đã tồn tại");
            throw new DuplicateFieldException(errors);

        }
        }
        User user = userService.getCurrentUserByUserName();
        Shipper s;

        // Nếu là cập nhật, lấy shipper hiện có, nếu không tạo mới
        if (isUpdate) {
            s = user.getShipper();
        } else {
            HashSet<Role> roles = new HashSet<>();
            roleRepository.findById(PredefinedRole.SHIPPER_ROLE).ifPresent(roles::add);
            roleRepository.findById(PredefinedRole.USER_ROLE).ifPresent(roles::add);
            user.setRoles(roles);
            s = new Shipper();
            s.setUser(user);
        }
        System.out.println(avatar);
        // Xử lý upload avatar nếu có
        if (avatar != null && !avatar.isEmpty()) {

            try {
                Map res = this.cloudinary.uploader().upload(avatar.getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                user.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        if (avatar != null && !avatar.isEmpty() || !isUpdate) {userRepository.save(user);}
        if(cmnd!=null && !cmnd.isEmpty()){
        s.setCmnd(cmnd);}
        s.setActive(false);
        shipperRepository.save(s);
    }



}
