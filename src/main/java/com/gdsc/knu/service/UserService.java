package com.gdsc.knu.service;

import com.gdsc.knu.dto.request.UpdateUserRequestDto;
import com.gdsc.knu.dto.response.GetUserResponseDto;
import com.gdsc.knu.dto.response.UpdateUserResponseDto;
import com.gdsc.knu.entity.User;
import com.gdsc.knu.exception.ForbiddenException;
import com.gdsc.knu.exception.NotFoundException;
import com.gdsc.knu.exception.UnauthorizedException;
import com.gdsc.knu.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public GetUserResponseDto getUser(Long id) {
         User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 사용자가 없습니다. id=" + id));
        return new GetUserResponseDto(user);
    }

    public UpdateUserResponseDto updateUser(UpdateUserRequestDto updateUserRequestDto, Authentication authentication) {
        String authEmail = authentication.getName();
        User requestUser = userRepository.findById(updateUserRequestDto.getId())
                .orElseThrow(() -> new NotFoundException("해당 사용자가 없습니다. id=" + updateUserRequestDto.getId()));

        if (!authEmail.equals(requestUser.getEmail())) throw new ForbiddenException("해당 사용자에 대한 수정 권한이 없습니다.");

        requestUser.updatePartial(updateUserRequestDto.getNickname(), updateUserRequestDto.getName());
        userRepository.save(requestUser);

        return new UpdateUserResponseDto(requestUser);
    }

    public List<GetUserResponseDto> searchUserWithNickname(String nickname) {
        List<User> users = userRepository.findByNicknameContaining(nickname);
        if (users.isEmpty()) throw new NotFoundException("해당 닉네임을 포함하는 사용자가 없습니다. nickname=" + nickname);
        return users.stream().map(GetUserResponseDto::new).collect(Collectors.toList());
    }

    public GetUserResponseDto getMe(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UnauthorizedException("로그인이 필요합니다."));
        return new GetUserResponseDto(user);
    }
}
