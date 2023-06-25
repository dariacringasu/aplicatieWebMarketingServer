package com.store.project.service.dao;

import com.store.project.domain.FileData;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface FileDataService {
    public String uploadImageToFileSystem(MultipartFile file) throws IOException;

    public FileData getById(Long id);

    public List<FileData> getAll();

    public void deteleFileById(long id);


}
