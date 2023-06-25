package com.store.project.service.implementation;

import com.store.project.domain.FileData;
import com.store.project.domain.Video;
import com.store.project.repository.FileDataRepository;
import com.store.project.service.dao.FileDataService;
import com.store.project.service.dao.VideoService;
import com.store.project.utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class FileDataServiceImplementation implements FileDataService {
    @Autowired
    private FileDataRepository fileDataRepository;
//    private VideoService videoService;

    @Autowired
    public FileDataServiceImplementation(FileDataRepository fileDataRepository){
        this.fileDataRepository=fileDataRepository;
//        this.videoService=videoService;
    }

    private final String FOLDER_PATH="D:\\vuejs\\my-app\\licenta\\src\\videos\\";

    public String uploadImageToFileSystem(MultipartFile file/*, long videoId*/) throws IOException {
        String filePath=FOLDER_PATH+file.getOriginalFilename();
//        Video video = videoService.findById(videoId);
        FileData fileData=fileDataRepository.save(FileData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes()))
                .filePath(filePath).build());
//        fileData.setVideo(video);
        file.transferTo(new File(filePath));

        if (fileData != null) {
            return "file uploaded successfully : " + filePath;
        }
        return null;
    }

    public FileData getById(Long id) {
        return fileDataRepository.findById(id);
    }

    public List<FileData> getAll(){ return fileDataRepository.findAll();}

    public void deteleFileById(long id){
        fileDataRepository.deleteById(id);
    }
}
