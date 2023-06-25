package com.store.project.repository;

import com.store.project.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User save(User user);

    List<User> findAll();

    User findById(long userId);

    Page<User> findAll(Pageable page);

    Optional<User> findByEmail(String email);

    void deleteById(long id);


}
