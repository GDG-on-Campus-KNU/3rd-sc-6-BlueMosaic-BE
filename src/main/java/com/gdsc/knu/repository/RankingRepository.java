package com.gdsc.knu.repository;

import com.gdsc.knu.entity.Ranking;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RankingRepository extends JpaRepository<Ranking, Long> {
    @Query("SELECT r.user, SUM(r.score) AS total_score FROM Ranking r GROUP BY r.user ORDER BY total_score DESC")
    List<Ranking> findTopRankingsWithDistinctUsers(Pageable pageable);

}
