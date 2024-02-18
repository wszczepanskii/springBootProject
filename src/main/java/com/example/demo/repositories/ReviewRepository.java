package com.example.demo.repositories;

import com.example.demo.domain.Review;
import org.springframework.data.repository.CrudRepository;


public interface ReviewRepository extends CrudRepository<Review, Long> {

}
