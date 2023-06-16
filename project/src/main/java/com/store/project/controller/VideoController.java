package com.store.project.controller;

import com.store.project.domain.Video;
import com.store.project.repository.UserRepository;
import com.store.project.service.dao.UserService;
import com.store.project.service.dao.VideoService;
import com.store.project.utils.ImageUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;



@RestController
@RequestMapping(value="/video")
public class VideoController {

    private VideoService videoService;
    private UserRepository userRepository;

    private final String FOLDER_PATH="D:\\vuejs\\my-app\\licenta\\src\\videos\\";

    private VideoController(VideoService videoService, UserRepository userRepository){
        this.videoService=videoService;
        this.userRepository=userRepository;
    }


    @ResponseBody
    @PostMapping(value="/add/{clientId}")
    public Video addVideo(@PathVariable long clientId, @RequestParam("title") String title,@RequestParam("description") String description, @RequestParam("urlmoreinfo") String urlmoreinfo,  @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        Video video = new Video();
        video.setClient(userRepository.findById(clientId));
        video.setVideoData(ImageUtils.compressImage(file.getBytes()));
        video.setTitle(title);
        video.setDescription(description);
        video.setFileName(file.getOriginalFilename());
        video.setURLmoreInfo(urlmoreinfo);
        video.setFilePath(FOLDER_PATH+file.getOriginalFilename());
        return videoService.addVideo(video,clientId,file);
    }
//    public Video addVideo(@RequestBody Video video, @PathVariable long clientId, ) throws IOException {
//         return videoService.addVideo(video,clientId);
//
//    }

    @ResponseBody
    @GetMapping(value="/getAll")
    public List<Video> getAll(){
        return videoService.getAll();
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
    @GetMapping(value = "/getApprovedNumber")
    public int getApprovedNumber( boolean type){
        return videoService.findAllByApprove(true).size();

    }

    @ResponseBody
    @DeleteMapping(value = "/delete/{id}")
    public void deleteVideoAndFileDataById(@PathVariable long id){
        videoService.deleteById(id);
    }
}
