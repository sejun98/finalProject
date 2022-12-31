package com.example.firstproject.config.auth;

import com.example.firstproject.entity.User;
import com.example.firstproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        return optionalUser
                .map(PrincipalDetails::new) // 입력받은 username에 해당하는 사용자가 있다면, PrincipalDetails 객체를 생성한다.
                .orElse(null); // 없다면 null을 반환한다. (인증 실패)
    }
}