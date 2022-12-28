package com.example.firstproject.DTO;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@Getter
@Setter
public class UserDto {

    private Long id;

    private String name;

    private String nickname;

    private String password;

    private String phnum;

}
