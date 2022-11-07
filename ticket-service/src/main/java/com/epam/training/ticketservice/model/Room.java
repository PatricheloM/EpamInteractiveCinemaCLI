package com.epam.training.ticketservice.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.List;

@Entity
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "rows", nullable = false)
    private Integer rows;

    @Column(name = "columns", nullable = false)
    private Integer columns;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Screening> screenings;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getColumns() {
        return columns;
    }

    public void setColumns(Integer columns) {
        this.columns = columns;
    }

    public Integer getSeats() {
        return rows * columns;
    }

    public List<Screening> getScreenings() {
        return screenings;
    }

    @Override
    public String toString() {
        return "Room " + name + " with "
                + rows * columns + " seats, "
                + rows + " rows and "
                + columns + " columns";
    }
}
