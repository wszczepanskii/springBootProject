package com.example.demo.bootstrap;

import com.example.demo.domain.*;
import com.example.demo.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class BootstrapData implements CommandLineRunner {

    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;

    public BootstrapData(MovieRepository movieRepository, ReviewRepository reviewRepository) {
        this.movieRepository = movieRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Movie> moviesList = new ArrayList<Movie>();
        List<Review> reviewsList = new ArrayList<Review>();

        System.out.println("Started in Bootstrap");

        Movie movie1 = new Movie("Harry potter i Kamień Filozoficzny", "Fantasy", "2001", "Daniel Radcliffe, Rupert Grint, Richard Harris", "W dniu jedenastych urodzin Harry dowiaduje się, że jest czarodziejem. Czeka na niego szkoła magii pełna tajemnic.", "https://fwcdn.pl/fpo/05/71/30571/7564040.3.jpg");
        Movie movie2 = new Movie("Fast and Furious", "Akcja", "2009", "Vin Diesel, Paul Walker, Michelle Rodriguez", "Przestępca oraz tropiący go agent łączą swoje siły i przenikają do narkotykowego gangu, by dopaść jego przywódcę.", "https://fwcdn.pl/fpo/15/04/461504/7246352.3.jpg");
        Movie movie3 = new Movie("Zielona Mila", "Dramat", "1999","Tom Hanks, Michael Clarke Duncan, David Morse", "Emerytowany strażnik więzienny opowiada przyjaciółce o niezwykłym mężczyźnie, którego skazano na śmierć za zabójstwo dwóch 9-letnich dziewczynek.", "https://fwcdn.pl/fpo/08/62/862/7097170.3.jpg");
        Movie movie4 = new Movie("Interstellar", "Sci-Fi", "2014","Matthew McConaughey, Anne Hathaway, Jessica Chastain", "Byt ludzkości na Ziemi dobiega końca wskutek zmian klimatycznych. Grupa naukowców odkrywa tunel czasoprzestrzenny, który umożliwia poszukiwanie nowego domu.", "https://fwcdn.pl/fpo/56/29/375629/7670122.3.jpg");
        Movie movie5 = new Movie("Avengers: Koniec Gry", "Akcja, Sci-Fi", "2019","Robert Downey Jr., Chris Evans, Mark Ruffalo", "Po wymazaniu połowy życia we Wszechświecie przez Thanosa Avengersi starają się zrobić wszystko, co konieczne, aby pokonać szalonego tytana. zmian klimatycznych. Grupa naukowców odkrywa tunel czasoprzestrzenny, który umożliwia poszukiwanie nowego domu.", "https://fwcdn.pl/fpo/05/42/790542/7881430.3.jpg");


        Review review1 = new Review("dobry film polecam bardzo !!!", "Marek");
        Review review2 = new Review("super film, ogladalem z rodzinka!", "Tomek");
        Review review3 = new Review("slaby film, nie polecam fdhuias dhasiuofd huiasdh asiuofd huiasdhasi uofd huiasdhas iuofdhuias dhasiuofd huiasdhas iuofdh uias dhasiuo", "Asia");
        Review review4_3 = new Review("Piękny film, bardzo wzruszający", "Grzesiek");
        Review review5_4 = new Review("Świetne efekty specjalne!","Andrzej");
        Review review6_5 = new Review("Film pełen akcji!", "Stefan");

        review1.setMovie(movie1);
        movie1.getReviews().add(review1);

        review2.setMovie(movie1);
        movie1.getReviews().add(review2);

        review3.setMovie(movie2);
        movie2.getReviews().add(review3);

        review4_3.setMovie(movie3);
        movie3.getReviews().add(review4_3);

        review5_4.setMovie(movie4);
        movie4.getReviews().add(review5_4);

        review6_5.setMovie(movie5);
        movie5.getReviews().add(review6_5);

        Collections.addAll(moviesList, movie1, movie2, movie3, movie4, movie5);
        Collections.addAll(reviewsList, review1, review2, review3, review4_3, review5_4, review6_5);

        movieRepository.saveAll(moviesList);
        reviewRepository.saveAll(reviewsList);
    }
}
