package com.store.project.service.implementation;

import com.store.project.controller.token.Token;
import com.store.project.domain.Role;
import com.store.project.domain.User;
import com.store.project.repository.UserRepository;
import com.store.project.service.dao.UserService;
import com.store.project.service.dao.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {

    UserRepository clientRepository;



    @Autowired
    public UserServiceImplementation(UserRepository clientRepository){
        this.clientRepository=clientRepository;
    }



    public User addClient(User client){
//        String encodedPassword = bCryptPasswordEncoder.encode(client.getPassword());
//        client.setPassword(encodedPassword);
//        client.setRole(Role.USER);


        return clientRepository.save(client);
    }

    public List<User> getAll(){
        return clientRepository.findAll();
    }

    public User findById(long clientId){
        return clientRepository.findById(clientId);
    }

    public Page<User> getUsersWithImageAndEmail(Pageable page) {
        Page<User> pageOfUsersWithImageAndEmai = clientRepository.findAll(page);
        return pageOfUsersWithImageAndEmai;
    }

    public User setUserRole(long id){
        User user = clientRepository.findById(id);

       user.setRole(Role.ADMIN);
       clientRepository.save(user);
        return user;
    }

    public void deleteById(long id){

        if (Objects.equals(id, null)) {
            // Handle the scenario where the id parameter is null
            throw new IllegalArgumentException("Invalid user ID");
        }
        clientRepository.deleteById(id);
    }

    public boolean emailExists(String email) {
        if (clientRepository.findByEmail(email).isPresent()) {
            return true;
        }
        return false;
    }

    public User unwrapUser(Optional<User> optionalUser) {
        return optionalUser.orElseThrow(() -> new RuntimeException("Optional user is empty"));
    }

    public void confirmAccount(String email){
        Optional<User> optionalUser = clientRepository.findByEmail(email);
        User user = unwrapUser(optionalUser);
        user.setEnabled(true);
        clientRepository.save(user);
    }

    public void confirmAllAccounts(){
        List<User> users = clientRepository.findAll();
        for(User user : users){
            user.setEnabled(true);
            clientRepository.save(user);
        }
    }
}
