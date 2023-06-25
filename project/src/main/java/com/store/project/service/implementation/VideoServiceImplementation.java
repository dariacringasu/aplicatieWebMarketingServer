package com.store.project.service.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.project.domain.FileData;
import com.store.project.domain.User;
import com.store.project.domain.Video;
import com.store.project.repository.FileDataRepository;
import com.store.project.repository.VideoRepository;
import com.store.project.service.dao.FileDataService;
import com.store.project.service.dao.StatisticsService;
import com.store.project.service.dao.UserService;
import com.store.project.service.dao.VideoService;
import com.store.project.utils.ImageUtils;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.core.type.TypeReference;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class VideoServiceImplementation implements VideoService {
    @Autowired
    UserService clientService;
    VideoRepository videoRepository;
    FileDataService fileDataService;


    @Autowired
    public VideoServiceImplementation(VideoRepository videoRepository, UserService clientService, FileDataService fileDataService){
        this.clientService = clientService;
        this.videoRepository = videoRepository;

        this.fileDataService = fileDataService;
    }

    private final String FOLDER_PATH="D:\\vuejs\\my-app\\licenta\\src\\videos\\";

    public Video addVideo(Video video, long clientId) throws IOException {
        User client=clientService.findById(clientId);
        video.setClient(client);
        video.setApproved(false);
        return videoRepository.save(video);

    }

    public List<Video> getAll(){
        return videoRepository.findAll();
    }

    public Video findById(long id){ return  videoRepository.findById(id);}

    public Long findUserIdById(long id){
        Video video = videoRepository.findById(id);
        return video.getClient().getId();
    }

    public Video setApprove(long id) throws IOException {
        Video video = videoRepository.findById(id);
        video.setApproved(true);

        String existingJson = Files.readString(Path.of("D:\\vuejs\\my-app\\licenta\\src\\videos\\names.json"));
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> videoNames = objectMapper.readValue(existingJson, new TypeReference<List<String>>() {});
        FileData fileData = fileDataService.getById(id);
        String fileDataName = fileData.getName();
        videoNames.add(fileDataName);

        String updatedJson = objectMapper.writeValueAsString(videoNames);
        Files.writeString(Path.of("D:\\vuejs\\my-app\\licenta\\src\\videos\\names.json"), updatedJson);

        return videoRepository.save(video);
    }

    public List<Video> findAllByApprove(boolean type){
        return videoRepository.findByApproved(type);
    }

    public  ResponseEntity<List<Object>> findByClientId(Long clientId){
        List<Video> videos = videoRepository.findByClientId(clientId);
        List<Object> videosDetails = new ArrayList<>();

        for(Video video : videos){
            List<Object> pair = new ArrayList<>();
            Long id = video.getId();
            String title = video.getTitle();
//            videosDetails.add(id.toString());
//            videosDetails.add(title);
            pair.add(id);
            pair.add(title);
            videosDetails.add(pair);
//            pair.clear();
        }
        return  ResponseEntity.ok(videosDetails);
    }

    public Video findByUrlmoreinfo(String urlmoreinfo){
        return videoRepository.findByUrlmoreinfo(urlmoreinfo);
    }
    @Transactional
    public void deleteById(long id) throws IOException {
       Video video = videoRepository.findById(id);
       FileData fileData = fileDataService.getById(id);

        String existingJson = Files.readString(Path.of("D:\\vuejs\\my-app\\licenta\\src\\videos\\names.json"));
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> videoNames = objectMapper.readValue(existingJson, new TypeReference<List<String>>() {});
        String fileDataName = fileData.getName();
        if(videoNames.contains(fileDataName)) {
            videoNames.remove(fileDataName);
            String updatedJson = objectMapper.writeValueAsString(videoNames);
            Files.writeString(Path.of("D:\\vuejs\\my-app\\licenta\\src\\videos\\names.json"), updatedJson);
        } else System.out.println("acest video nu a fost aprobat");

        File file = new File(FOLDER_PATH+ fileDataName);
        if(file.exists()){
            file.delete();
            System.out.println("File deleted successfully");
        } else System.out.println("File does not exist!");
        fileDataService.deteleFileById(id);
        videoRepository.deleteById(id);
    }

}
