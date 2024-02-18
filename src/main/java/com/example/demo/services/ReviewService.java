package com.example.demo.services;

import com.example.demo.domain.Movie;
import com.example.demo.domain.Review;
//import com.example.demo.domain.ReviewUser;
import com.example.demo.repositories.MovieRepository;
import com.example.demo.repositories.ReviewRepository;
//import com.example.demo.repositories.ReviewUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> findAll(){
        return (List<Review>) reviewRepository.findAll();
    }

    public Optional<Review> findById(Long id){
        return reviewRepository.findById(id);
    }
}
