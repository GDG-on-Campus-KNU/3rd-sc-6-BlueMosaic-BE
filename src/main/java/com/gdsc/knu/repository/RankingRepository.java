package com.gdsc.knu.repository;

import com.gdsc.knu.entity.Ranking;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RankingRepository extends JpaRepository<Ranking, Long> {
    @Query("SELECT r FROM Ranking r WHERE r.score IN (SELECT MAX(r2.score) FROM Ranking r2 GROUP BY r2.user) ORDER BY r.score DESC")
    List<Ranking> findTopRankingsWithDistinctUsers(Pageable pageable);

}
