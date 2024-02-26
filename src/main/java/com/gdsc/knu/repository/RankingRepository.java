package com.gdsc.knu.repository;

import com.gdsc.knu.dto.UserRankingDto;
import com.gdsc.knu.entity.Ranking;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RankingRepository extends JpaRepository<Ranking, Long> {
    @Query("SELECT new com.gdsc.knu.dto.UserRankingDto(r.user, sum(r.score)) FROM Ranking r GROUP BY r.user ORDER BY sum(r.score) DESC")
    List<UserRankingDto> findUserRanking(Pageable pageable);
}
