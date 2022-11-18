package com.epam.training.ticketservice.command;

import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.model.enums.AccountPrivilege;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.util.session.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class MovieTest {

    private Movie movie;

    @InjectMocks
    private MovieCommands movieCommands;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private Session session;

    @BeforeEach
    public void beforeEach() {
        movie = new Movie();
    }

    @Test
    public void testShouldReturnNullWhenCreatingMovieGivenAdminSession() {

        // Given

        // When
        Mockito.when(session.getPrivilege()).thenReturn(AccountPrivilege.ADMIN);
        Mockito.when(movieRepository.findByName("Avatar")).thenReturn(Optional.empty());
        String result = movieCommands.createMovie("Avatar", "sci-fi", 160);

        // Then
        Assertions.assertNull(result);
    }

    @Test
    public void testShouldReturnErrorWhenCreatingMovieGivenUserSession() {

        // Given

        // When
        Mockito.when(session.getPrivilege()).thenReturn(AccountPrivilege.USER);
        String result = movieCommands.createMovie("Avatar", "sci-fi", 160);

        // Then
        Assertions.assertEquals("You are not signed in as an admin", result);
    }

    @Test
    public void testShouldReturnErrorWhenCreatingExistingMovieGivenAdminSession() {

        // Given
        movie.setName("Avatar");
        movie.setGenre("comedy");
        movie.setLength(90);

        // When
        Mockito.when(session.getPrivilege()).thenReturn(AccountPrivilege.ADMIN);
        Mockito.when(movieRepository.findByName("Avatar")).thenReturn(Optional.of(movie));
        String result = movieCommands.createMovie("Avatar", "sci-fi", 160);

        // Then
        Assertions.assertEquals("Movie already exists with name 'Avatar'", result);
    }

    @Test
    public void testShouldReturnNullWhenUpdatingExistingMovieGivenAdminSession() {

        // Given
        movie.setName("Avatar");
        movie.setGenre("comedy");
        movie.setLength(90);

        // When
        Mockito.when(session.getPrivilege()).thenReturn(AccountPrivilege.ADMIN);
        Mockito.when(movieRepository.findByName("Avatar")).thenReturn(Optional.of(movie));
        String result = movieCommands.updateMovie("Avatar", "sci-fi", 160);

        // Then
        Assertions.assertNull(result);
    }

    @Test
    public void testShouldReturnErrorWhenUpdatingMovieGivenUserSession() {

        // Given

        // When
        Mockito.when(session.getPrivilege()).thenReturn(AccountPrivilege.USER);
        String result = movieCommands.updateMovie("Avatar", "sci-fi", 160);

        // Then
        Assertions.assertEquals("You are not signed in as an admin", result);
    }

    @Test
    public void testShouldReturnErrorWhenUpdatingNonExistingMovieGivenAdminSession() {

        // Given

        // When
        Mockito.when(session.getPrivilege()).thenReturn(AccountPrivilege.ADMIN);
        Mockito.when(movieRepository.findByName("Avatar")).thenReturn(Optional.empty());
        String result = movieCommands.updateMovie("Avatar", "sci-fi", 160);

        // Then
        Assertions.assertEquals("Movie does not exists with name 'Avatar'", result);
    }

    @Test
    public void testShouldReturnNullWhenDeletingExistingMovieGivenAdminSession() {

        // Given
        movie.setName("Avatar");
        movie.setGenre("sci-fi");
        movie.setLength(160);

        // When
        Mockito.when(session.getPrivilege()).thenReturn(AccountPrivilege.ADMIN);
        Mockito.when(movieRepository.findByName("Avatar")).thenReturn(Optional.of(movie));
        String result = movieCommands.deleteMovie("Avatar");

        // Then
        Assertions.assertNull(result);
    }

    @Test
    public void testShouldReturnErrorWhenDeletingExistingMovieGivenUserSession() {

        // Given

        // When
        Mockito.when(session.getPrivilege()).thenReturn(AccountPrivilege.USER);
        String result = movieCommands.deleteMovie("Avatar");

        // Then
        Assertions.assertEquals("You are not signed in as an admin", result);
    }

    @Test
    public void testShouldReturnErrorWhenDeletingNonExistingMovieGivenAdminSession() {

        // Given

        // When
        Mockito.when(session.getPrivilege()).thenReturn(AccountPrivilege.ADMIN);
        Mockito.when(movieRepository.findByName("Avatar")).thenReturn(Optional.empty());
        String result = movieCommands.deleteMovie("Avatar");

        // Then
        Assertions.assertEquals("Movie does not exists with name 'Avatar'", result);
    }

    @Test
    public void testShouldReturnInfoWhenListingMoviesGivenNoMoviesInRepository() {

        // Given


        // When
        Mockito.when(movieRepository.findAll()).thenReturn(Collections.emptyList());
        String result = movieCommands.listMovies();

        // Then
        Assertions.assertEquals("There are no movies at the moment", result);
    }

    @Test
    public void testShouldReturnNullWhenListingMoviesGivenOneMovieInRepository() {

        // Given
        movie.setName("Avatar");
        movie.setGenre("sci-fi");
        movie.setLength(160);

        // When
        Mockito.when(movieRepository.findAll()).thenReturn(List.of(movie));
        String result = movieCommands.listMovies();

        // Then
        Assertions.assertNull(result);
    }

    @Test
    public void testShouldReturnMovieWhenPrinting() {

        // Given
        movie.setName("Avatar");
        movie.setGenre("sci-fi");
        movie.setLength(160);

        // When
        String result = movie.toString();


        // Then
        Assertions.assertEquals("Avatar (sci-fi, 160 minutes)", result);
    }

}
