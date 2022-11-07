package com.epam.training.ticketservice.command;

import com.epam.training.ticketservice.model.Room;
import com.epam.training.ticketservice.model.enums.AccountPrivilege;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.util.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;
import java.util.Optional;

@ShellComponent
public class RoomCommands {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    Session session;

    private static final AccountPrivilege ADMIN = AccountPrivilege.ADMIN;

    @ShellMethod(value = "Create new room.", key = "create room")
    public String createRoom(String name, Integer rows, Integer columns) {
        if (session.getPrivilege() == ADMIN) {
            Optional<Room> existsCheck = roomRepository.findByName(name);
            if (existsCheck.isPresent()) {
                return "Room already exists with name '" + name + "'";
            } else {
                Room room = new Room();
                room.setName(name);
                room.setRows(rows);
                room.setColumns(columns);
                roomRepository.save(room);
                return null;
            }
        } else {
            return "You are not signed in as an admin";
        }
    }

    @ShellMethod(value = "Update an existing room.", key = "update room")
    public String updateRoom(String name, Integer rows, Integer columns) {
        if (session.getPrivilege() == ADMIN) {
            Optional<Room> existsCheck = roomRepository.findByName(name);
            if (existsCheck.isEmpty()) {
                return "Room does not exists with name '" + name + "'";
            } else {
                Room room = existsCheck.get();
                room.setRows(rows);
                room.setColumns(columns);
                roomRepository.save(room);
                return null;
            }
        } else {
            return "You are not signed in as an admin";
        }
    }

    @ShellMethod(value = "Delete an existing room.", key = "delete room")
    public String deleteRoom(String name) {
        if (session.getPrivilege() == ADMIN) {
            Optional<Room> existsCheck = roomRepository.findByName(name);
            if (existsCheck.isEmpty()) {
                return "Room does not exists with name '" + name + "'";
            } else {
                roomRepository.deleteById(existsCheck.get().getId());
                return null;
            }
        } else {
            return "You are not signed in as an admin";
        }
    }

    @ShellMethod(value = "List all rooms.", key = "list rooms")
    public String listRooms() {
        List<Room> rooms = roomRepository.findAll();
        if (rooms.isEmpty()) {
            return "There are no rooms at the moment";
        } else {
            for (Room room : rooms) {
                System.out.println(room);
            }
            return null;
        }
    }
}
