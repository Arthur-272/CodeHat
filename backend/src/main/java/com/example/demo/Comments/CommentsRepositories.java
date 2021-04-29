package com.example.demo.Comments;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentsRepositories extends CrudRepository<Comments, Long> {
}
