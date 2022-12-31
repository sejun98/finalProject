package com.example.firstproject.repository;

import com.example.firstproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // username을 가지고 User 정보를 가져올 수 있게 메소드 생성
    Optional<User> findByUsername(String username);
}
