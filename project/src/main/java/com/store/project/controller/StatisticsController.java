package com.store.project.controller;


import com.store.project.domain.Statistics;
import com.store.project.service.dao.StatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/stats")
public class StatisticsController {

    private StatisticsService service;

    public StatisticsController(StatisticsService service){
        this.service = service;
    }

    @ResponseBody
    @PostMapping(value = "/add/{videoId}/{userId}")
    public Statistics add(@RequestBody Statistics statistics, @PathVariable long videoId, @PathVariable Long userId){
        return service.addStatistics(statistics, videoId, userId);
    }

    @ResponseBody
    @GetMapping
    public List<Statistics> getAll(){
        return service.getAll();
    }

    @ResponseBody
    @GetMapping(value = "/{videoId}")
    public List<Statistics> findByVideoId(@PathVariable long videoId){
        return service.findByVideoId(videoId);

    }

    @ResponseBody
    @GetMapping(value = "/byUserId/{userId}")
    public List<Statistics> findByUserId(@PathVariable Long userId) {
        return service.findByUserId(userId);
    }

    @ResponseBody
    @GetMapping(value = "/mostAppearedLocations/{userId}")
    public ResponseEntity<List<String>> findMostAppearedLocationsByUserId(@PathVariable Long userId){
        return service.findMostAppearedLocationsByUserId(userId);
    }
}
