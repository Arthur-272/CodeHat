package com.example.demo.EmailConfirmation;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailConfirmationRepositories extends CrudRepository      <EmailConfirmation,String> {
    EmailConfirmation findByConfirmationToken(String confirmationToken);
}
