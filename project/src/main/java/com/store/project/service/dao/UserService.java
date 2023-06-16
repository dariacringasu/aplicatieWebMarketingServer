package com.store.project.service.dao;

import com.store.project.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public User addClient(User client);

    public List<User> getAll();

    public User findById(long clientId);

    public Page<User> getUsersWithImageAndEmail(Pageable page);

    public User setUserRole(long id);

    public void deleteById(long id);

    public boolean emailExists(String email);

    public void confirmAccount(String email);
}
