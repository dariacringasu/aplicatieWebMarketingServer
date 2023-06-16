package com.store.project.service.dao;

import com.store.project.domain.Video;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface VideoService {

    public Video addVideo(Video video, long clientId, MultipartFile file) throws IOException;

    public List<Video> getAll();
    public Video findById(long id);

    public Video setApprove(long id) throws IOException;

    public List<Video> findAllByApprove(boolean type);

    public void deleteById(long id);
}
