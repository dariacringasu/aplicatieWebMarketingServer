package com.store.project.controller;

import com.store.project.domain.User;
import com.store.project.domain.Video;
import com.store.project.repository.UserRepository;
import com.store.project.service.dao.StatisticsService;
import com.store.project.service.dao.UserService;
import com.store.project.service.dao.VideoService;
import com.store.project.utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;



@RestController
@RequestMapping(value="/video")
public class VideoController {

    @Autowired
    private VideoService videoService;
    private UserRepository userRepository;
    private StatisticsService statisticsService;

    private final String FOLDER_PATH="D:\\vuejs\\my-app\\licenta\\src\\videos\\";

    public VideoController(VideoService videoService, UserRepository userRepository, StatisticsService statisticsService){
        this.videoService=videoService;
        this.userRepository=userRepository;
        this.statisticsService = statisticsService;
    }


    @ResponseBody
    @PostMapping(value="/add/{clientId}")
//    public Video addVideo(@PathVariable long clientId, @RequestParam("title") String title,@RequestParam("description") String description, @RequestParam("urlmoreinfo") String urlmoreinfo,  @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
//        Video video = new Video();
//        video.setClient(userRepository.findById(clientId));
//        video.setVideoData(ImageUtils.compressImage(file.getBytes()));
//        video.setTitle(title);
//        video.setType(file.getContentType());
//        video.setDescription(description);
//        video.setFileName(file.getOriginalFilename());
//        video.setURLmoreInfo(urlmoreinfo);
//        video.setFilePath(FOLDER_PATH+file.getOriginalFilename());
//        file.transferTo(new File(video.getFilePath()));
//        return videoService.addVideo(video,clientId,file);
//    }
    public Video addVideo(@RequestBody Video video, @PathVariable long clientId) throws IOException {
         return videoService.addVideo(video,clientId);

    }

    @ResponseBody
    @GetMapping(value="/getAll")
    public List<Video> getAll(){
        return videoService.getAll();
    }

    @ResponseBody
    @GetMapping(value="/getUserIdById/{id}")
    public Long getUserIdById(@PathVariable long id){
        return videoService.findUserIdById(id);
    }


    @ResponseBody
    @PutMapping(value = "/setApprove/{id}")
    public Video setApprove(@PathVariable long id) throws IOException {
        return videoService.setApprove(id);
    }

    @ResponseBody
    @GetMapping(value = "/findByApprove/{type}")
    public List<Video> findByApprove(@PathVariable boolean type){
        return videoService.findAllByApprove(type);
    }

    @ResponseBody
    @GetMapping(value = "/findByClientId/{clientId}")
    public ResponseEntity<List<Object>> findByClientId(@PathVariable Long clientId){
        return videoService.findByClientId(clientId);
    }

    @ResponseBody
    @GetMapping(value = "/getApprovedNumber")
    public int getApprovedNumber( boolean type){
        return videoService.findAllByApprove(true).size();

    }

    @ResponseBody
    @GetMapping(value = "/getByUrl/{urlmoreinfo}")
    public Video findByUrlmoreinfo(@PathVariable String urlmoreinfo){
        return videoService.findByUrlmoreinfo(urlmoreinfo);
    }

    @ResponseBody
    @DeleteMapping(value = "/delete/{id}")
    @Transactional
    public void deleteVideoAndFileDataById(@PathVariable long id) throws IOException {
        statisticsService.deleteByVideoId(id);
        videoService.deleteById(id);
    }


}
