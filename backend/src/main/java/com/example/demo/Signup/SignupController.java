package com.example.demo.Signup;

import com.example.demo.Users.Users;
import com.example.demo.Users.UsersServices;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@CrossOrigin
public class SignupController {

    @Autowired
    private UsersServices usersServices;

    @RequestMapping("/view")
    public List<Users> getUsers(){
        return usersServices.getUsers();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/signup")
    public ResponseEntity add(@RequestBody Users user) throws Exception{
        return usersServices.addUser(user);
    }
    @RequestMapping(value="/confirm-account",method = {RequestMethod.POST,RequestMethod.GET})
    public ModelAndView confirmUserAccount(ModelAndView modelAndView,@RequestParam("token") String confirmationToken){
        usersServices.confirmAccount(confirmationToken);
        modelAndView.setViewName("sucessfulRegistration");
        return  modelAndView;
    }
}