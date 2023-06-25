package com.store.project.controller;


import com.store.project.domain.FileData;
import com.store.project.service.dao.FileDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/image")
public class FileDataController {
    @Autowired
    private FileDataService service;

    @PostMapping("/addFile")
    public ResponseEntity<?> uploadImageToFIleSystem(@RequestParam("image") MultipartFile file/*, @RequestParam long videoId*/) throws IOException {
        String uploadImageToFolder = service.uploadImageToFileSystem(file);

        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImageToFolder);

    }

    @GetMapping("/getFileDataNameById/{id}")
    public FileData getFileDataNameById(@PathVariable Long id){
        return service.getById(id);
    }

    @GetMapping("/getAll")
    public List<FileData> getAll(){ return service.getAll();}

    @Transactional
    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable long id){
        service.deteleFileById(id);
    }
}
