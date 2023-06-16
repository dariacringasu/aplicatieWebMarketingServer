package com.store.project.repository;

import com.store.project.domain.FileData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface FileDataRepository extends JpaRepository<FileData,Integer> {
    FileData findByName(String fileName);
    List<FileData> findAll();

    FileData findById(Long id);

    void deleteById(long id);
}