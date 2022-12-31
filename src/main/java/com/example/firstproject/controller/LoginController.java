package com.example.firstproject.controller;

import com.example.firstproject.entity.User;
import com.example.firstproject.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class LoginController {

    @Autowired private UserRepository userRepository; // 글 아래에서 생성할 예정
    @Autowired private BCryptPasswordEncoder passwordEncoder; // 시큐리티에서 빈(Bean) 생성할 예정

    /**
     * 인덱스 페이지
     *
     * @return
     */
    @GetMapping({"", "/"})
    public String index() {
        return "index";
    }

    /**
     * 유저 페이지
     *
     * @return
     */
    @GetMapping("user")
    public String user() {
        return "user";
    }

    /**
     * 로그인 폼 페이지
     *
     * @return
     */
    @GetMapping("loginform")
    public String loginForm() {
        return "page/loginForm";
    }

    /**
     * 회원 가입 페이지
     *
     * @return
     */
    @GetMapping("joinform")
    public String joinForm() {
        return "page/joinForm";
    }
    @GetMapping("joingood")
    public String joingood() {
        return "page/message";
    }
    @GetMapping("/loginindex")
    public String loginindex() {
        return "page/loginindex";
    }

    /**
     * 회원 가입이 실행되는 부분
     *
     * @param user
     * @return
     */
    @PostMapping("join")
    public String join(User user, Model model) {
        user.setRole("ROLE_ADMIN"); // 권한 정보는 임시로 ROLE_ADMIN으로 넣는다.
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        model.addAttribute("msg","회원가입이 완료되었습니다");

        return "redirect:/loginform";
    }
}