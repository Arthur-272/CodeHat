package com.example.demo.Login;

import com.example.demo.Users.Users;
import com.example.demo.Users.UsersRepositories;
import com.example.demo.Users.UsersServices;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.Optional;

@Service
public class LoginServices {

    @Autowired
    private UsersServices usersServices;

    @Autowired
    private UsersRepositories usersRepositories;

    public ResponseEntity login(Login credentials) throws Exception{

        Optional<Users> u = usersRepositories.findByEmail(credentials.getEmail());
        Long id = null;
        if(u.isPresent()){
            id = u.get().getId();
        }
        if(u.isPresent() && u.get().isEnabled()){
            Users userFetchedFromDB = usersServices.getUserById(id);
            if(userFetchedFromDB.getPassword().equals(credentials.getPassword())){
                userFetchedFromDB.setLastLoggedInDate(new Date());
                usersServices.save(userFetchedFromDB);
            } else{
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "entity not found");
            }
        } else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
        }
        return ResponseEntity.ok().body(u);
    }
}
