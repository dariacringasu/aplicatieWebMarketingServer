package com.store.project.controller;

import com.store.project.domain.User;
import com.store.project.service.dao.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value="/client")
public class UserController {

    private UserService clientService;

    public UserController(UserService clientService) {
        this.clientService = clientService;
    }

    //adaugare client si inregistrare mutate in authentication controller
//    @ResponseBody
//    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public Client addClient(@RequestBody Client client){
//        return clientService.addClient(client);
//    }

    @ResponseBody
    @GetMapping(value="/getAll")
    public List<User> getAll(){
        return clientService.getAll();
    }

    @ResponseBody
    @GetMapping(value="/getUsersNumber")
    public int getUsersByNumber(){
        return clientService.getAll().size();
    }

    @ResponseBody
    @GetMapping(value="/getUserById")
    public User getUserById(@RequestParam Long userId){
        return clientService.findById(userId);
    }


    @ResponseBody
    @GetMapping(value = "/paged/basicDetails")
    public ResponseEntity<Page<User>> findAllWithImageAndEmail(@RequestParam Integer page,
                                                               @RequestParam Integer numberOfUsersPerPage) {
        Pageable nextUsers = PageRequest.of(page, numberOfUsersPerPage);
        Page<User> usersPage = clientService.getUsersWithImageAndEmail(nextUsers);
        if (usersPage.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(usersPage);
    }

    @ResponseBody
    @PostMapping(value = "/setAdmin/{id}")
    public User setAdmin(@PathVariable long id){
        return clientService.setUserRole(id);
    }

    @ResponseBody
    @PostMapping(value = "/confirmAccount/{email}")
    public String confirmAccount(@PathVariable String email){
        clientService.confirmAccount(email);
        return "Email validat";
    }

    @ResponseBody
    @DeleteMapping(value = "/deleteUser/{id}")
    public void deleteById(@PathVariable long id){
        clientService.deleteById(id);
    }
}
