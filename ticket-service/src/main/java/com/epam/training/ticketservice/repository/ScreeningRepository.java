package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.model.Room;
import com.epam.training.ticketservice.model.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ScreeningRepository extends JpaRepository<Screening, Integer> {
    @Query(value = "FROM Screening\nWHERE (MOVIE_NAME = ?1 AND ROOM_NAME = ?2 AND SCREENING_START = ?3)")
    Optional<Screening> findOne(Movie movie, Room room, LocalDateTime start);
}
