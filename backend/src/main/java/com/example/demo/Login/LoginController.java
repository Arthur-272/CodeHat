package com.example.demo.Login;

import com.example.demo.Users.Users;
import com.example.demo.Users.UsersRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

@RestController
@CrossOrigin
public class LoginController {
    @Autowired
    private LoginServices loginServices;

    @RequestMapping(method = RequestMethod.POST, value="/login")
    public ResponseEntity login(@RequestBody Login credentials) throws Exception{
        return loginServices.login(credentials);

    }
}
