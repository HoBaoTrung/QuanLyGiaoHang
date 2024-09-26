package com.hbt.QuanLyGiaoHangBackend.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.hbt.QuanLyGiaoHangBackend.pojo.Product;
import com.hbt.QuanLyGiaoHangBackend.pojo.Proof;
import com.hbt.QuanLyGiaoHangBackend.repositories.ProductRepository;
import com.hbt.QuanLyGiaoHangBackend.repositories.ProofRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Transactional
@Service
public class ProofService {

    @Autowired
    private ProofRepository proofRepository;

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private ProductRepository productRepository;


    public Proof saveOrUpdateProof(Long productId, MultipartFile image) {
        // Kiểm tra xem Product có tồn tại không
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Sản phẩm không tồn tại với id: " + productId));

        // Tìm kiếm Proof dựa trên productId
        Proof proof = proofRepository.findById(productId).orElse(new Proof());

        // Cập nhật thông tin của Proof
        proof.setProduct(product);
        if (image != null && !image.isEmpty()) {
            try {
                Map res = this.cloudinary.uploader().upload(image.getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                proof.setImage(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        // Lưu hoặc cập nhật Proof vào cơ sở dữ liệu
        return proofRepository.save(proof);
    }
}
