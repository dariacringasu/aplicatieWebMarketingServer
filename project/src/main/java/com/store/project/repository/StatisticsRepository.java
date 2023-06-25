package com.store.project.repository;

import com.store.project.domain.Statistics;
import com.store.project.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, Long> {

    Statistics save(Statistics statistics);

    List<Statistics> findAll();

    List<Statistics> findByVideoId(long videoId);

    List<Statistics> findByUserId(Long userId);

    @Query("SELECT s.location, COUNT(s.location) as locationCount FROM Statistics s WHERE s.user.id = :userId GROUP BY s.location ORDER BY locationCount DESC")
    List<Object[]> findMostAppearedLocationsByUserId(Long userId, Pageable pageable);

    void deleteByVideoId(long videoId);
}
