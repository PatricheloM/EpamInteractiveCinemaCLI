package com.epam.training.ticketservice.model;

import com.epam.training.ticketservice.util.converter.LocalDateTimeConverter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.UniqueConstraint;
import java.time.LocalDateTime;

@Entity
@Table(name = "screening", uniqueConstraints = {@UniqueConstraint(
                columnNames = {"movie.name", "room.name", "screening_start"})})
public class Screening {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "movie.name")
    private Movie movie;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room.name")
    private Room room;

    @Column(name = "screening_start", columnDefinition = "TIMESTAMP")
    private LocalDateTime screeningStart;

    @Column(name = "screening_end", columnDefinition = "TIMESTAMP")
    private LocalDateTime screeningEnd;

    @Column(name = "break_end", columnDefinition = "TIMESTAMP")
    private LocalDateTime breakEnd;

    public Integer getId() {
        return id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public LocalDateTime getScreeningStart() {
        return screeningStart;
    }

    public void setScreeningStart(LocalDateTime screeningStart) {
        this.screeningStart = screeningStart;
        this.screeningEnd = screeningStart.plusMinutes(movie.getLength());
        this.breakEnd = screeningStart.plusMinutes(movie.getLength() + 10);
    }

    public LocalDateTime getScreeningEnd() {
        return screeningEnd;
    }

    public LocalDateTime getBreakEnd() {
        return breakEnd;
    }

    @Override
    public String toString() {
        return movie.getName()
                + " (" + movie.getGenre() + ", "
                + movie.getLength() + " minutes),"
                + " screened in room " + room.getName()
                + ", at " + LocalDateTimeConverter.localDateTimeToString(screeningStart);
    }
}
