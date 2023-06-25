package com.store.project.service.implementation;

import com.store.project.domain.Statistics;
import com.store.project.domain.User;
import com.store.project.domain.Video;
import com.store.project.repository.StatisticsRepository;
import com.store.project.service.dao.StatisticsService;
import com.store.project.service.dao.UserService;
import com.store.project.service.dao.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticsServiceImplementation implements StatisticsService {

    @Autowired
    StatisticsRepository repository;
    VideoService videoService;
    UserService userService;


    @Autowired
    public StatisticsServiceImplementation(StatisticsRepository repository, VideoService videoService, UserService userService){
        this.repository = repository;
        this.videoService = videoService;
        this.userService = userService;
    }

    public Statistics addStatistics(Statistics statistics, long videoId, Long userId) {
        Video video = videoService.findById(videoId);
        User user = userService.findById(userId);
        statistics.setVideo(video);
        statistics.setUser(user);
        return repository.save(statistics);
    }

    public List<Statistics> getAll() {
        return repository.findAll();
    }

    public List<Statistics> findByVideoId(long videoId) {
        return repository.findByVideoId(videoId);
    }

    public List<Statistics> findByUserId(Long userId){
        return  repository.findByUserId(userId);
    }

    public ResponseEntity<List<String>> findMostAppearedLocationsByUserId(Long userId){
        Pageable pageable = PageRequest.of(0,2);

        List<Object[]> locationsData = repository.findMostAppearedLocationsByUserId(userId, pageable);
        List<String> locations = new ArrayList<>();

        for(Object[] locationData : locationsData){
            String location = (String) locationData[0];
            locations.add(location);
        }
        return ResponseEntity.ok(locations);
    }

    public void deleteByVideoId(long videoId) {
        repository.deleteByVideoId(videoId);
    }
}
