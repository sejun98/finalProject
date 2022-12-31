package com.example.firstproject.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor // 기본 생성자 어노테이션
@AllArgsConstructor
public class User {
    //PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Column
    private String nickname;

    @Column
    private String password;

    @Column
    private String phnum;

    @Column
    private String role;

    @CreationTimestamp
    private LocalDateTime createdDate;
}
