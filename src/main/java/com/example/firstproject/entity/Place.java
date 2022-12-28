package com.example.firstproject.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor // 디폴트 생성자 어노테이션
@ToString
@Getter
@Setter
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String titleplace;

    @Column
    private String content;

    @Column
    private String state;

    @Column
    private String theme;

    @Column
    private String filename;

    @Column
    private String filepath;

    @Column
    private String address;

    @Column
    private String benefit;

}
