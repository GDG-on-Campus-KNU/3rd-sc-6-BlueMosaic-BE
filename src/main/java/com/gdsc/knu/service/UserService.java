package com.gdsc.knu.service;

import com.gdsc.knu.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;


    /**
     * 사용자의 생성은 OAuth2UserService에서 이루어진다.
     * 그에 따라 read, update, delete만 구현한다.
     */

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
