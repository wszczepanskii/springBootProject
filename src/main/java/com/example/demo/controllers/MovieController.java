package com.example.demo.controllers;

import com.example.demo.domain.Movie;
import com.example.demo.domain.Review;
import com.example.demo.repositories.ReviewRepository;
import com.example.demo.services.MovieService;
import com.example.demo.services.ReviewService;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.ui.Model;
import com.example.demo.repositories.MovieRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@Controller
public class MovieController {
    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;

    public MovieController(MovieRepository movieRepository, ReviewRepository reviewRepository) {
        this.movieRepository = movieRepository;
        this.reviewRepository = reviewRepository;
    }

//    @GetMapping("/movies")
//    public String getMovies(Model model){
//
//        model.addAttribute("movies", movieRepository.findAll());
//        model.addAttribute("reviews", reviewRepository.findAll());
//        return "movies/list";
//    }
//
//    @GetMapping("/reviews/{id}")
//    public String showReview(@PathVariable("id") Long id, Model model){
//        Review review = reviewRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + id));
//
//        model.addAttribute("review", review);
//
//        return "movies/list";
//    }
//
//    @GetMapping("/")
//    public String index(){
//        return "layout";
//    }

    @Autowired
    private MovieService movieService;
    @Autowired
    private ReviewService reviewService;

    public void checkAdmin(Authentication authentication, Model model){
        if (authentication != null && authentication.isAuthenticated()) {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            boolean isAdmin = authorities.stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

            model.addAttribute("isAdmin", isAdmin);
        }
    }

    @GetMapping("/")
    public String index(Model model, Authentication authentication){
        checkAdmin(authentication, model);

        Iterable<Movie> movies = movieService.findAll();

        model.addAttribute("movies", movies);
        model.addAttribute("contentTemplate", "fragments/index");

        return "layout";
    }

    @GetMapping("/movies/{id}/reviews")
    public String getReviewsByMovie(@PathVariable("id") Long id, Model model){
        List<Review> review = movieService.getReviewsByMovie(id);

        model.addAttribute("review", review);
        model.addAttribute("movie", movieService.findById(id).orElse(null));
        model.addAttribute("reviewUser", reviewService.findById(id).orElse(null));
        model.addAttribute("contentTemplate", "fragments/movieDetails");
        return "layout";
    }


    @GetMapping("/createReview/{id}")
    public String createReview(@PathVariable("id") Long id, Model model){
        List<Review> review = movieService.getReviewsByMovie(id);
        model.addAttribute("review", review);
        model.addAttribute("movie", movieService.findById(id).orElse(null));

        model.addAttribute("review", new Review());
        model.addAttribute("contentTemplate", "fragments/createReview");

        return "layout";
    }


    @PostMapping("/createReview/{id}")
    public String createNewReview(@ModelAttribute("review") Review review, @PathVariable("id") Long id){
        Review newReview = new Review();
        newReview.setMovie(movieService.findById(id).orElse(null));
        newReview.setUsername(review.getUsername());
        newReview.setReviewDescription(review.getReviewDescription());
        reviewRepository.save(newReview);

        return "redirect:/movies/{id}/reviews";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/createMovie")
    public String showCreateMovieForm(Authentication authentication, Model model){
        checkAdmin(authentication, model);

        model.addAttribute("movie", new Movie());
        model.addAttribute("contentTemplate", "fragments/createMovie");

        return "layout";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/createMovie")
    public String createMovie(Authentication authentication, Model model, @ModelAttribute("movie") Movie movie){
        checkAdmin(authentication, model);

        Movie newMovie = new Movie();
        newMovie.setTitle(movie.getTitle());
        newMovie.setActors(movie.getActors());
        newMovie.setGenre(movie.getGenre());
        newMovie.setDescription(movie.getDescription());
        newMovie.setImageUri(movie.getImageUri());
        newMovie.setReleaseDate(movie.getReleaseDate());
        movieRepository.save(newMovie);

        return "redirect:/adminPanel";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/editMovie/{id}")
    public String showEditMovieForm(Authentication authentication, @PathVariable("id") Long id, Model model){
        checkAdmin(authentication, model);

        Movie movie = movieService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + id));
        model.addAttribute("movie", movie);
        model.addAttribute("contentTemplate", "fragments/editMovie");

        return "layout";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/editMovie/{id}")
    public String editMovie(Authentication authentication, Model model, @PathVariable("id") Long id, @ModelAttribute("movie") Movie movie){
        checkAdmin(authentication, model);

        Movie editedMovie = movieService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + id));
        editedMovie.setTitle(movie.getTitle());
        editedMovie.setActors(movie.getActors());
        editedMovie.setGenre(movie.getGenre());
        editedMovie.setDescription(movie.getDescription());
        editedMovie.setImageUri(movie.getImageUri());
        editedMovie.setReleaseDate(movie.getReleaseDate());

        movieRepository.save(editedMovie);

        return "redirect:/adminPanel";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/editReview/{id}")
    public String showEditReviewForm(Authentication authentication, @PathVariable("id") Long id, Model model){
        checkAdmin(authentication, model);

        Review review = reviewService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid review ID: " + id));
        model.addAttribute("review", review);
        model.addAttribute("contentTemplate", "fragments/editReview");

        return "layout";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/editReview/{id}")
    public String editReview(Authentication authentication, Model model, @PathVariable("id") Long id, @ModelAttribute("movie") Review review){
        checkAdmin(authentication, model);

        Review editedReview = reviewService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid review ID: " + id));
        editedReview.setReviewDescription(review.getReviewDescription());

        reviewRepository.save(editedReview);

        return "redirect:/adminPanel";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/adminPanel")
    public String showAdminPanel(Model model, Authentication authentication){
        checkAdmin(authentication, model);

        Iterable<Movie> movies = movieService.findAll();
        Iterable<Review> reviews = reviewService.findAll();
        model.addAttribute("movies", movies);
        model.addAttribute("reviews", reviews);
        model.addAttribute("contentTemplate", "fragments/adminPanel");

        return "layout";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/deleteMovie/{id}")
    public String deleteMovie(Authentication authentication, Model model, @PathVariable Long id){
        checkAdmin(authentication, model);

        movieRepository.deleteById(id);

        return "redirect:/adminPanel";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/deleteReview/{id}")
    public String deleteReview(Authentication authentication, Model model, @PathVariable("id") Long id){
        checkAdmin(authentication, model);

        reviewRepository.deleteById(id);

        return  "redirect:/adminPanel";
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("contentTemplate", "fragments/login");

        return "layout";
    }

    @ControllerAdvice
    public class GlobalExceptionHandler {

        @ExceptionHandler(Exception.class)
        public String handleException(Model model,Authentication authentication) {
            if (authentication != null && authentication.isAuthenticated()) {
                Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
                boolean isAdmin = authorities.stream()
                        .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

                model.addAttribute("isAdmin", isAdmin);
            }
            model.addAttribute("contentTemplate", "fragments/general-error");
            return "layout"; // Ustaw nazwę swojej strony błędu ogólnego
        }
    }



//    @GetMapping("movies/{id}/reviews")
//    public List<Review> getReviewsByMovie(@PathVariable("id") Long id){
//
//        return movieService.getReviewsByMovie(id);
//    }


//    @GetMapping("/movies/{id}/reviews")
//    public List<Review> getReviewsByMovie(@PathVariable("id") Long id){
//
//    }
//    @RequestMapping("/reviews")
//    public String getReviews(Model model){
//
//        model.addAttribute("reviews", reviewRepository.findAll());
//        return "movies/list";
//    }
}
