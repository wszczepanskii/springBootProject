package com.example.demo.repositories;

import com.example.demo.domain.Movie;
import com.example.demo.domain.Review;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MovieRepository extends CrudRepository<Movie, Long> {

    @Query("SELECT m.reviews FROM Movie m WHERE m.id = ?1")
    List<Review> getReviewsByMovie(Long id);
}
