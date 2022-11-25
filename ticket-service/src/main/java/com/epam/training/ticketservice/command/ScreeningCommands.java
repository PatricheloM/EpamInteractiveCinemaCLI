package com.epam.training.ticketservice.command;

import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.model.Room;
import com.epam.training.ticketservice.model.Screening;
import com.epam.training.ticketservice.model.enums.AccountPrivilege;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.repository.ScreeningRepository;
import com.epam.training.ticketservice.util.converter.LocalDateTimeConverter;
import com.epam.training.ticketservice.util.session.Session;
import com.github.mawippel.validator.OverlappingVerificator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@ShellComponent
public class ScreeningCommands {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    ScreeningRepository screeningRepository;

    @Autowired
    Session session;

    private static final AccountPrivilege ADMIN = AccountPrivilege.ADMIN;

    @ShellMethod(value = "Create new screening.", key = "create screening")
    public String createScreening(String movieName, String roomName, String time) {
        if (session.getPrivilege() == ADMIN) {
            Optional<Movie> movieExists = movieRepository.findByName(movieName);
            Optional<Room> roomExists = roomRepository.findByName(roomName);
            LocalDateTime start;
            try {
                start = LocalDateTimeConverter.stringToLocalDateTime(time);
            } catch (DateTimeParseException e) {
                return "Wrong time format, use \"YYYY-MM-DD hh:mm\"";
            }
            if (movieExists.isEmpty() || roomExists.isEmpty()) {
                return "Movie '" + movieName + "' or Room '" + roomName + "' does not exist";
            } else {
                for (Screening screening : screeningRepository.findAll()) {
                    if (Objects.equals(roomExists.get().getName(), screening.getRoom().getName())
                                && OverlappingVerificator.isOverlap(start,
                                start.plusMinutes(movieExists.get().getLength()),
                                screening.getScreeningStart(), screening.getScreeningEnd())) {
                        return "There is an overlapping screening";
                    } else if (Objects.equals(roomExists.get().getName(), screening.getRoom().getName())
                                && OverlappingVerificator.isOverlap(start,
                                start.plusMinutes(movieExists.get().getLength() + 9),
                                screening.getScreeningStart(), screening.getBreakEnd())) {
                        return "This would start in the break period after another screening in this room";
                    }
                }
                Screening screening = new Screening();
                screening.setMovie(movieExists.get());
                screening.setRoom(roomExists.get());
                screening.setScreeningStart(start);
                screeningRepository.save(screening);
                return null;
            }
        } else {
            return "You are not signed in as an admin";
        }
    }

    @ShellMethod(value = "Delete existing screening.", key = "delete screening")
    public String deleteScreening(String movieName, String roomName, String time) {
        if (session.getPrivilege() == ADMIN) {
            Optional<Movie> movieExists = movieRepository.findByName(movieName);
            Optional<Room> roomExists = roomRepository.findByName(roomName);
            LocalDateTime start;
            try {
                start = LocalDateTimeConverter.stringToLocalDateTime(time);
            } catch (DateTimeParseException e) {
                return "Wrong time format, use \"YYYY-MM-DD hh:mm\"";
            }
            if (movieExists.isEmpty() || roomExists.isEmpty()) {
                return "Movie '" + movieName + "' or Room '" + roomName + "' does not exist";
            } else {
                Optional<Screening> exists = screeningRepository.findOne(movieExists.get(), roomExists.get(), start);
                if (exists.isEmpty()) {
                    return "No screenings were found for '" + time + "'";
                } else {
                    screeningRepository.delete(exists.get());
                    return null;
                }
            }
        } else {
            return "You are not signed in as an admin";
        }
    }

    @ShellMethod(value = "List all screenings.", key = "list screenings")
    public String listScreenings() {
        List<Screening> screenings = screeningRepository.findAll();
        if (screenings.isEmpty()) {
            return "There are no screenings";
        } else {
            for (Screening screening : screenings) {
                System.out.println(screening);
            }
            return null;
        }
    }
}
