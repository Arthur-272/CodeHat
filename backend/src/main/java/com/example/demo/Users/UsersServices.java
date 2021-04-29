package com.example.demo.Users;

import com.example.demo.EmailConfirmation.EmailConfirmation;
import com.example.demo.EmailConfirmation.EmailConfirmationRepositories;
import com.example.demo.EmailConfirmation.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UsersServices {
    @Autowired
    private UsersRepositories usersRepositories;
    @Autowired
    private EmailConfirmationRepositories emailConfirmationRepositories;
    @Autowired
    private EmailService emailService;
    public void updateUser(long id, Users user) throws Exception{
        if(checkIfUserExists(id)){
//            The following fields cannot be changed by user.
            Users userFetchedFromDB = usersRepositories.findById(id).get();
            user.setPassword(userFetchedFromDB.getPassword());
            user.setRole(userFetchedFromDB.getRole());
            user.setScore(userFetchedFromDB.getScore());
            usersRepositories.save(user);

        }
        else {
            throw new Exception("User not found!");
        }
    }

    public boolean checkIfUserExists(long id){
        Optional<Users> list = usersRepositories.findById(id);
        if(list.isEmpty()){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean isTeacher(long id){
        return usersRepositories.findById(id).get().getRole().equals("teacher");
    }

    public boolean isStudent(long id){
        return usersRepositories.findById(id).get().getRole().equals("student");
    }

    public Users getUserById(long id){
        return usersRepositories.findById(id).get();
    }

    public Optional<Users> getUserByEmail(String email){
        return usersRepositories.findByEmail(email);
    }

    public void save(Users user){
        usersRepositories.save(user);
    }

    public ResponseEntity addUser(Users user) throws Exception {
        Optional<Users> list = usersRepositories.findByEmail(user.getEmail());
        if(list.isEmpty()){
            user.setRegisteredDate(new Date());
            user.setEnabled(false);
            usersRepositories.save(user);

            // send-mail
            EmailConfirmation emailConfirmation = new EmailConfirmation(user);
            emailConfirmationRepositories.save(emailConfirmation);

            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(user.getEmail());
            simpleMailMessage.setFrom("18it032@charusat.edu.in");
            simpleMailMessage.setSubject("Complete Registration!!");
            simpleMailMessage.setText("To confirm you account, please click here:" + "http://localhost:8080/confirm-account?token="+emailConfirmation.getConfirmationToken());
            emailService.sendEmail(simpleMailMessage);

            return ResponseEntity.ok().build();
        } else{
            throw new ResponseStatusException(HttpStatus.CONFLICT, "user already exists");
        }
    }
    public ResponseEntity confirmAccount(String token){
        EmailConfirmation emailConfirmation = emailConfirmationRepositories.findByConfirmationToken(token);
        if(emailConfirmation!=null){
            Optional<Users> user = usersRepositories.findByEmailIgnoreCase(emailConfirmation.getUser().getEmail());
            if(user.isPresent()){
                Users u = getUsers().get(0);
                if(u!=null){
                    u.setEnabled(true);
                    usersRepositories.save(u);
                    return ResponseEntity.ok().build();
                }
                else
                    throw new ResponseStatusException(HttpStatus.CONFLICT,"something bad happend");
            }else{
                throw new ResponseStatusException(HttpStatus.CONFLICT,"No such user");
            }
        }else{
            throw new ResponseStatusException(HttpStatus.CONFLICT,"The link is invalid or broken");
        }

    }
    public List<Users> getUsers(){
        List<Users> list = new ArrayList<Users>();
        usersRepositories.findAll().forEach(list::add);
        return list;
    }

    Optional<List<Users>> getUsersByHighestScore(){
        return usersRepositories.findAllByOrderByScoreDesc();
    }
}
