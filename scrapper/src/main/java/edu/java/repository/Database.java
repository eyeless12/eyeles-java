package edu.java.repository;

import edu.java.model.Chat;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

// игрушечная база данных
@AllArgsConstructor
@Getter
public class Database {
    private long linkCounter;
    private List<Chat> chats;

    public void incrementCounter() {
        linkCounter++;
    }
}
