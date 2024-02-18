package com.example.demo.services;

import com.example.demo.domain.Movie;
import com.example.demo.domain.Review;
import com.example.demo.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> findAll(){
        return (List<Movie>) movieRepository.findAll();
    }

    public Optional<Movie> findById(Long id){
        return movieRepository.findById(id);
    }

    public List<Review> getReviewsByMovie(Long id){
        return movieRepository.getReviewsByMovie(id);
    }

}
