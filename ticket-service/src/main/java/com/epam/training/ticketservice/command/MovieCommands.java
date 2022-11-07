package com.epam.training.ticketservice.command;

import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.model.enums.AccountPrivilege;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.util.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;
import java.util.Optional;

@ShellComponent
public class MovieCommands {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    Session session;

    private static final AccountPrivilege ADMIN = AccountPrivilege.ADMIN;

    @ShellMethod(value = "Create new movie.", key = "create movie")
    public String createMovie(String name, String genre, Integer length) {
        if (session.getPrivilege() == ADMIN) {
            Optional<Movie> existsCheck = movieRepository.findByName(name);
            if (existsCheck.isPresent()) {
                return "Movie already exists with name '" + name + "'";
            } else {
                Movie movie = new Movie();
                movie.setName(name);
                movie.setGenre(genre);
                movie.setLength(length);
                movieRepository.save(movie);
                return null;
            }
        } else {
            return "You are not signed in as an admin";
        }
    }

    @ShellMethod(value = "Update an existing movie.", key = "update movie")
    public String updateMovie(String name, String genre, Integer length) {
        if (session.getPrivilege() == ADMIN) {
            Optional<Movie> existsCheck = movieRepository.findByName(name);
            if (existsCheck.isEmpty()) {
                return "Movie does not exists with name '" + name + "'";
            } else {
                Movie movie = existsCheck.get();
                movie.setGenre(genre);
                movie.setLength(length);
                movieRepository.save(movie);
                return null;
            }
        } else {
            return "You are not signed in as an admin";
        }
    }

    @ShellMethod(value = "Delete an existing movie.", key = "delete movie")
    public String deleteMovie(String name) {
        if (session.getPrivilege() == ADMIN) {
            Optional<Movie> existsCheck = movieRepository.findByName(name);
            if (existsCheck.isEmpty()) {
                return "Movie does not exists with name '" + name + "'";
            } else {
                movieRepository.deleteById(existsCheck.get().getId());
                return null;
            }
        } else {
            return "You are not signed in as an admin";
        }
    }

    @ShellMethod(value = "List all movies.", key = "list movies")
    public String listMovies() {
        List<Movie> movies = movieRepository.findAll();
        if (movies.isEmpty()) {
            return "There are no movies at the moment";
        } else {
            for (Movie movie : movies) {
                System.out.println(movie);
            }
            return null;
        }
    }
}
