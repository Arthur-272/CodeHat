package com.example.demo.Users;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepositories extends CrudRepository<Users, Long> {

    Optional<Users> findByEmail(String email);
    Optional<Users> findByEmailIgnoreCase(String email);
    Optional<List<Users>> findAllByOrderByScoreDesc();
}
