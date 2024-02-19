package com.gdsc.knu.service;

import com.gdsc.knu.dto.response.GetRankingResponseDto;
import com.gdsc.knu.dto.response.GetUserResponseDto;
import com.gdsc.knu.entity.Ranking;
import com.gdsc.knu.entity.User;
import com.gdsc.knu.exception.NotFoundException;
import com.gdsc.knu.repository.RankingRepository;
import com.gdsc.knu.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RankingService {
    private final RankingRepository rankingRepository;
    private final UserRepository userRepository;

    public void makeTestData(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new NotFoundException("해당 사용자가 없습니다. id=" + authentication.getName()));

        for (int i = 0; i < 10; i++) {
            Ranking ranking = new Ranking(
                    user,
                    (int) (Math.random() * 1000)
            );
            Ranking rnk = rankingRepository.save(ranking);
        }
    }

    public List<GetRankingResponseDto> getRankingTOP100() {
        List<Ranking> rankings = rankingRepository.findTopRankingsWithDistinctUsers(PageRequest.of(0, 100, Sort.by(Sort.Direction.DESC, "score")));
        if (rankings.isEmpty()) throw new NotFoundException("랭킹이 없습니다.");
        return rankings.stream().map(GetRankingResponseDto::new).collect(Collectors.toList());
    }
}
