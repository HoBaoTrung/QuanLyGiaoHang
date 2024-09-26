package com.hbt.QuanLyGiaoHangBackend.repositories;

import com.hbt.QuanLyGiaoHangBackend.pojo.Product;
import com.hbt.QuanLyGiaoHangBackend.pojo.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
   Optional<User> findByUsername(String username);
   boolean existsByEmail(String email);
   boolean existsByPhone(String phone);
   boolean existsByUsername(String username);
   Page<User> findAll(Pageable pageable);

}
