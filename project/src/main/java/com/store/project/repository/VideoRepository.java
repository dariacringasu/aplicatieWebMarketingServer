package com.store.project.repository;

import com.store.project.domain.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    Video save(Video video);

    List<Video> findAll();

    Video findById(long id);

    List<Video> findByApproved(boolean type);

    void deleteById(long id);
}
