package com.store.project.service.implementation;

import com.store.project.domain.FileData;
import com.store.project.domain.User;
import com.store.project.domain.Video;
import com.store.project.repository.FileDataRepository;
import com.store.project.repository.VideoRepository;
import com.store.project.service.dao.UserService;
import com.store.project.service.dao.VideoService;
import com.store.project.utils.ImageUtils;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

@Service
public class VideoServiceImplementation implements VideoService {
    @Autowired
    UserService clientService;
    VideoRepository videoRepository;
    FileDataRepository fileDataRepository;

    @Autowired
    public VideoServiceImplementation(VideoRepository videoRepository, UserService clientService, FileDataRepository fileDataRepository){
        this.clientService = clientService;
        this.videoRepository = videoRepository;
        this.fileDataRepository = fileDataRepository;
    }

    private final String FOLDER_PATH="D:\\vuejs\\my-app\\licenta\\src\\videos\\";

    public Video addVideo(Video video, long clientId, MultipartFile file) throws IOException {
        User client=clientService.findById(clientId);
        video.setClient(client);
        video.setApproved(false);
        return videoRepository.save(video);

    }

    public List<Video> getAll(){
        return videoRepository.findAll();
    }

    public Video findById(long id){ return videoRepository.findById(id);}

    public Video setApprove(long id) throws IOException {
        Video video = videoRepository.findById(id);
        video.setApproved(true);
        Path videoFilePath = Path.of(video.getFilePath());
        Files.write(videoFilePath, video.getVideoData(), StandardOpenOption.CREATE);
        return videoRepository.save(video);
    }

    public List<Video> findAllByApprove(boolean type){
        return videoRepository.findByApproved(type);
    }


    public void deleteById(long id){
       Video video = videoRepository.findById(id);
       File videoFile = new File(video.getFilePath());
       videoFile.delete();
       videoRepository.deleteById(id);
    }
}
