package com.store.project.service.dao;

import com.store.project.domain.Statistics;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface StatisticsService {

    public Statistics addStatistics(Statistics statistics, long videoId, Long userId);

    public List<Statistics> getAll();

    public List<Statistics> findByVideoId(long videoId);

    public List<Statistics> findByUserId(Long userId);

    public ResponseEntity<List<String>> findMostAppearedLocationsByUserId(Long userId);

    public void deleteByVideoId(long videoId);
}
