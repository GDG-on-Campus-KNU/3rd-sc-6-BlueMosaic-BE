package com.gdsc.knu.service;

import com.gdsc.knu.entity.Ranking;
import com.gdsc.knu.repository.RankingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RankingService {
    private final RankingRepository rankingRepository;

    public void makeTestData() {
        for (int i = 0; i < 10; i++) {
            rankingRepository.save(new Ranking());
        }
    }
}
